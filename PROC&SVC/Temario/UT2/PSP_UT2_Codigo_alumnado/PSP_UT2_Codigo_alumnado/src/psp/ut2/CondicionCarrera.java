package psp.ut2;

/**
 * Cuatro hilos incrementan 100 000 veces cada uno el mismo contador.
 * El resultado debería ser 400 000, pero casi nunca lo es.
 */
public class CondicionCarrera {

    static class Contador {
        private int valor = 0;

        void incrementar() {
            valor++;               // leer, sumar 1 y escribir: NO es atómico
        }

        int getValor() {
            return valor;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Contador contador = new Contador();          // objeto compartido
        Thread[] hilos = new Thread[4];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < 100_000; j++) {
                    contador.incrementar();
                }
            });
            hilos[i].start();
        }
        for (Thread h : hilos) h.join();

        System.out.println("Valor esperado: 400000");
        System.out.println("Valor obtenido: " + contador.getValor());
    }
}
