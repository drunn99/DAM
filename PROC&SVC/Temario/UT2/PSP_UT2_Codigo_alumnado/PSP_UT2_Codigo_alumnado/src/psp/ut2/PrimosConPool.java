package psp.ut2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * El mismo problema de la UT1 (contar primos en un rango), resuelto ahora con hilos:
 * un pool de tamaño fijo ejecuta tareas Callable y devuelve los resultados con Future.
 * Uso: java psp.ut2.PrimosConPool [hasta] [tareas]
 */
public class PrimosConPool {

    /** Tarea con resultado: cuenta los primos del rango [desde, hasta]. */
    record ContarPrimos(long desde, long hasta) implements Callable<Long> {
        @Override
        public Long call() {
            long total = 0;
            for (long n = desde; n <= hasta; n++) {
                if (esPrimo(n)) total++;
            }
            return total;
        }
    }

    static boolean esPrimo(long n) {
        if (n < 2) return false;
        if (n % 2 == 0) return n == 2;
        for (long d = 3; d * d <= n; d += 2) {
            if (n % d == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long hasta = args.length > 0 ? Long.parseLong(args[0]) : 5_000_000;
        int numTareas = args.length > 1 ? Integer.parseInt(args[1]) : 16;
        int nucleos = Runtime.getRuntime().availableProcessors();

        // Versión secuencial, para comparar
        long t0 = System.nanoTime();
        long secuencial = new ContarPrimos(1, hasta).call();
        long msSec = (System.nanoTime() - t0) / 1_000_000;

        // Versión concurrente con un pool de tantos hilos como núcleos
        t0 = System.nanoTime();
        long total = 0;
        try (ExecutorService pool = Executors.newFixedThreadPool(nucleos)) {
            List<Future<Long>> futuros = new ArrayList<>();
            long tramo = hasta / numTareas;
            for (int i = 0; i < numTareas; i++) {
                long desde = i * tramo + 1;
                long fin = (i == numTareas - 1) ? hasta : (i + 1) * tramo;
                futuros.add(pool.submit(new ContarPrimos(desde, fin)));   // no bloquea
            }
            for (Future<Long> f : futuros) {
                total += f.get();                                // espera al resultado
            }
        }   // close(): espera a que terminen las tareas y apaga el pool
        long msPar = (System.nanoTime() - t0) / 1_000_000;

        System.out.printf("Núcleos: %d | Tareas: %d%n", nucleos, numTareas);
        System.out.printf("Secuencial:  %d primos en %d ms%n", secuencial, msSec);
        System.out.printf("Con el pool: %d primos en %d ms%n", total, msPar);
        System.out.printf("Aceleración: %.2f%n", (double) msSec / msPar);
    }
}
