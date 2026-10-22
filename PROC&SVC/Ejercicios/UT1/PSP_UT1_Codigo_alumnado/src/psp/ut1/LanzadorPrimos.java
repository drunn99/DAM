package psp.ut1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Proceso padre: reparte el rango [1, LIMITE] entre N procesos hijo,
 * los lanza en paralelo, espera a que terminen y suma sus resultados.
 */
public class LanzadorPrimos {

    private static final long LIMITE = 20_000_000L;

    public static void main(String[] args) throws IOException, InterruptedException {
        int numProcesos = args.length > 0 ? Integer.parseInt(args[0])
                                          : Runtime.getRuntime().availableProcessors();
        String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
        String classpath = System.getProperty("java.class.path");

        long inicio = System.nanoTime();
        long tramo = LIMITE / numProcesos;
        List<Process> hijos = new ArrayList<>();

        // 1) Lanzar TODOS los hijos sin esperar a ninguno
        for (int i = 0; i < numProcesos; i++) {
            long desde = i * tramo + 1;
            long hasta = (i == numProcesos - 1) ? LIMITE : (i + 1) * tramo;
            ProcessBuilder pb = new ProcessBuilder(java, "-cp", classpath,
                    "psp.ut1.ContadorPrimos", String.valueOf(desde), String.valueOf(hasta));
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process p = pb.start();
            System.out.printf("Hijo %d (PID %d): rango %d - %d%n", i, p.pid(), desde, hasta);
            hijos.add(p);
        }

        // 2) Recoger resultados y esperar a cada hijo
        long totalPrimos = 0;
        for (Process p : hijos) {
            try (BufferedReader br = p.inputReader()) {
                String linea = br.readLine();
                int codigo = p.waitFor();
                if (codigo == 0 && linea != null) {
                    totalPrimos += Long.parseLong(linea.trim());
                } else {
                    System.err.printf("El hijo %d ha fallado (código %d)%n", p.pid(), codigo);
                }
            }
        }

        long ms = (System.nanoTime() - inicio) / 1_000_000;
        System.out.printf("Primos hasta %d: %d%n", LIMITE, totalPrimos);
        System.out.printf("Procesos: %d | Tiempo: %d ms%n", numProcesos, ms);
    }
}
