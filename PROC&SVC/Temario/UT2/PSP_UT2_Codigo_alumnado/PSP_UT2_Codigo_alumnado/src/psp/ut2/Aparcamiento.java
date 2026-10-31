package psp.ut2;

import java.util.concurrent.Semaphore;

/**
 * Un aparcamiento con 3 plazas y 8 coches. Un semáforo con 3 permisos
 * garantiza que nunca haya más de 3 coches dentro a la vez.
 */
public class Aparcamiento {

    private static final Semaphore plazas = new Semaphore(3, true);   // justo (FIFO)

    public static void main(String[] args) throws InterruptedException {
        Thread[] coches = new Thread[8];
        for (int i = 0; i < coches.length; i++) {
            coches[i] = new Thread(Aparcamiento::aparcar, "Coche-" + (i + 1));
            coches[i].start();
        }
        for (Thread c : coches) c.join();
        System.out.println("Aparcamiento vacío. Plazas libres: " + plazas.availablePermits());
    }

    static void aparcar() {
        String coche = Thread.currentThread().getName();
        try {
            plazas.acquire();                      // espera si no hay plazas
            try {
                System.out.println(coche + " entra. Plazas libres: " + plazas.availablePermits());
                Thread.sleep((long) (Math.random() * 1000) + 500);
                System.out.println(coche + " sale");
            } finally {
                plazas.release();                  // libera la plaza siempre
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
