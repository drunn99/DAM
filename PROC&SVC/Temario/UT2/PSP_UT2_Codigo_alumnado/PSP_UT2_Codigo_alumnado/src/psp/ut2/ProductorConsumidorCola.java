package psp.ut2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Productor-consumidor con una BlockingQueue: la cola ya incluye toda la sincronización.
 * Dos productores y dos consumidores. Para terminar se usa una "píldora venenosa".
 */
public class ProductorConsumidorCola {

    private static final String FIN = "<FIN>";   // poison pill

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> cola = new ArrayBlockingQueue<>(5);
        int productores = 2, consumidores = 2;

        Thread[] prod = new Thread[productores];
        for (int p = 0; p < productores; p++) {
            int id = p;
            prod[p] = new Thread(() -> {
                try {
                    for (int i = 1; i <= 5; i++) {
                        cola.put("pedido " + id + "-" + i);   // espera si está llena
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Productor-" + p);
            prod[p].start();
        }

        Thread[] cons = new Thread[consumidores];
        for (int c = 0; c < consumidores; c++) {
            cons[c] = new Thread(() -> {
                try {
                    while (true) {
                        String pedido = cola.take();          // espera si está vacía
                        if (pedido.equals(FIN)) break;
                        String yo = Thread.currentThread().getName();
                        System.out.println(yo + " procesa " + pedido);
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Consumidor-" + c);
            cons[c].start();
        }

        for (Thread t : prod) t.join();            // cuando los productores acaban...
        for (int c = 0; c < consumidores; c++) {
            cola.put(FIN);                         // ...una píldora por consumidor
        }
        for (Thread t : cons) t.join();
        System.out.println("Todos los pedidos procesados");
    }
}
