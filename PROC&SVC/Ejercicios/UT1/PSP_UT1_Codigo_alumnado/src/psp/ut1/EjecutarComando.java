package psp.ut1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/** Ejecuta un comando del sistema y muestra su salida (versión Linux para pruebas). */
public class EjecutarComando {
    public static void main(String[] args) throws IOException, InterruptedException {
        boolean windows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        ProcessBuilder pb = windows ? new ProcessBuilder("cmd", "/c", "dir")
                                    : new ProcessBuilder("ls", "-l");
        pb.redirectErrorStream(true);
        Process p = pb.start();
        Charset cs = windows ? Charset.forName("CP850") : Charset.defaultCharset();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), cs))) {
            String linea;
            while ((linea = br.readLine()) != null) System.out.println(linea);
        }
        System.out.println("Código de salida: " + p.waitFor());
    }
}
