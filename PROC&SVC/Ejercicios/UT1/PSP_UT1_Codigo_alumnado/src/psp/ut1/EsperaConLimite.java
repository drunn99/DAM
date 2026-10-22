package psp.ut1;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class EsperaConLimite {
    public static void main(String[] args) throws IOException, InterruptedException {
        boolean windows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        ProcessBuilder pb = windows ? new ProcessBuilder("ping", "-n", "10", "127.0.0.1")
                                    : new ProcessBuilder("sleep", "10");
        pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
        Process p = pb.start();
        System.out.println("Lanzado el proceso " + p.pid());
        boolean terminado = p.waitFor(3, TimeUnit.SECONDS);
        if (!terminado) {
            System.out.println("Tiempo agotado: se finaliza el proceso");
            p.destroyForcibly();
            p.waitFor();
        }
        System.out.println("¿Sigue vivo? " + p.isAlive() + " | Código: " + p.exitValue());
    }
}
