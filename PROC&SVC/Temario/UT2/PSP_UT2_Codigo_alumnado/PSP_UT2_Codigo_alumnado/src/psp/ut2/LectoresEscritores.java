package psp.ut2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Catálogo de precios: muchos hilos lo consultan y pocos lo modifican.
 * Con un ReentrantReadWriteLock, varios lectores pueden leer a la vez,
 * pero un escritor necesita acceso exclusivo.
 */
public class LectoresEscritores {

    static class Catalogo {
        private final Map<String, Double> precios = new HashMap<>();
        private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock(true);

        Double consultar(String producto) throws InterruptedException {
            rw.readLock().lock();
            try {
                String yo = Thread.currentThread().getName();
                int activos = rw.getReadLockCount();
                System.out.println(yo + " LEE   (lectores activos: " + activos + ")");
                Thread.sleep(300);
                return precios.get(producto);
            } finally {
                rw.readLock().unlock();
            }
        }

        void actualizar(String producto, double precio) throws InterruptedException {
            rw.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " ESCRIBE (en exclusiva)");
                Thread.sleep(500);
                precios.put(producto, precio);
            } finally {
                rw.writeLock().unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Catalogo catalogo = new Catalogo();
        catalogo.actualizar("teclado", 25.0);

        Thread[] hilos = new Thread[6];
        for (int i = 0; i < hilos.length; i++) {
            boolean escritor = (i == 3);
            hilos[i] = new Thread(() -> {
                try {
                    if (escritor) catalogo.actualizar("teclado", 27.5);
                    else catalogo.consultar("teclado");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, escritor ? "Escritor" : "Lector-" + i);
        }
        for (Thread h : hilos) h.start();
        for (Thread h : hilos) h.join();
        System.out.println("Precio final: " + catalogo.consultar("teclado"));
    }
}
