package psp.ut2;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 10 000 tareas que pasan casi todo el tiempo esperando (simulan una consulta de red de 1 s).
 * Con hilos virtuales terminan en poco más de 1 segundo.
 * Uso: java psp.ut2.HilosVirtuales [virtual|plataforma]
 */
public class HilosVirtuales {

    public static void main(String[] args) {
        boolean virtuales = args.length == 0 || args[0].equals("virtual");
        AtomicInteger completadas = new AtomicInteger();
        long t0 = System.nanoTime();

        try (ExecutorService ejecutor = virtuales
                ? Executors.newVirtualThreadPerTaskExecutor()
                : Executors.newFixedThreadPool(200)) {
            for (int i = 0; i < 10_000; i++) {
                ejecutor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));   // espera de E/S simulada
                    completadas.incrementAndGet();
                    return null;
                });
            }
        }
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.printf("%s: %d tareas en %d ms%n",
                virtuales ? "Hilos virtuales" : "Pool de 200 hilos", completadas.get(), ms);
    }
}
