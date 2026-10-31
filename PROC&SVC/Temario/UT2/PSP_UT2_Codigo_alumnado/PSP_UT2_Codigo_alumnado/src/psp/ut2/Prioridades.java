package psp.ut2;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Lanza más hilos que núcleos, la mitad con prioridad máxima y la otra mitad con
 * prioridad mínima, y cuenta cuántas vueltas da cada uno en 3 segundos.
 * El resultado depende del sistema operativo: la prioridad es solo una sugerencia.
 */
public class Prioridades {

    static final AtomicBoolean seguir = new AtomicBoolean(true);

    static class Contador extends Thread {
        long vueltas = 0;

        Contador(String nombre, int prioridad) {
            super(nombre);
            setPriority(prioridad);
        }

        @Override
        public void run() {
            while (seguir.get()) {
                vueltas++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int nucleos = Runtime.getRuntime().availableProcessors();
        Contador[] hilos = new Contador[nucleos * 2];
        for (int i = 0; i < hilos.length; i++) {
            int prioridad = (i % 2 == 0) ? Thread.MAX_PRIORITY : Thread.MIN_PRIORITY;
            hilos[i] = new Contador("H" + i + "-p" + prioridad, prioridad);
        }
        for (Contador h : hilos) h.start();

        Thread.sleep(3000);
        seguir.set(false);

        long totalMax = 0, totalMin = 0;
        for (Contador h : hilos) {
            h.join();
            if (h.getPriority() == Thread.MAX_PRIORITY) totalMax += h.vueltas;
            else totalMin += h.vueltas;
        }
        System.out.printf("Vueltas prioridad 10: %,d%n", totalMax);
        System.out.printf("Vueltas prioridad 1:  %,d%n", totalMin);
    }
}
