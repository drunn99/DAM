package psp.ut2;

/**
 * Productor-consumidor con un monitor: buffer circular de capacidad fija
 * protegido con synchronized y coordinado con wait() / notifyAll().
 */
public class BufferMonitor {

    private final int[] datos;
    private int entrada = 0, salida = 0, cantidad = 0;

    public BufferMonitor(int capacidad) {
        datos = new int[capacidad];
    }

    public synchronized void poner(int valor) throws InterruptedException {
        while (cantidad == datos.length) {     // buffer lleno: el productor espera
            wait();
        }
        datos[entrada] = valor;
        entrada = (entrada + 1) % datos.length;
        cantidad++;
        notifyAll();                           // avisa a los consumidores que esperan
    }

    public synchronized int sacar() throws InterruptedException {
        while (cantidad == 0) {                // buffer vacío: el consumidor espera
            wait();
        }
        int valor = datos[salida];
        salida = (salida + 1) % datos.length;
        cantidad--;
        notifyAll();                           // avisa a los productores que esperan
        return valor;
    }

    public static void main(String[] args) throws InterruptedException {
        BufferMonitor buffer = new BufferMonitor(3);

        Thread productor = new Thread(() -> {
            try {
                for (int i = 1; i <= 8; i++) {
                    buffer.poner(i);
                    System.out.println("Productor pone   " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumidor = new Thread(() -> {
            try {
                for (int i = 1; i <= 8; i++) {
                    int v = buffer.sacar();
                    System.out.println("Consumidor saca  " + v);
                    Thread.sleep(200);         // el consumidor es más lento
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        productor.start();
        consumidor.start();
        productor.join();
        consumidor.join();
    }
}
