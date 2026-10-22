package psp.ut1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Proceso padre: envía texto a la entrada estándar del hijo Mayusculas
 * y lee lo que este devuelve por su salida estándar.
 */
public class ComunicacionHijo {
    public static void main(String[] args) throws IOException, InterruptedException {
        String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
        ProcessBuilder pb = new ProcessBuilder(java,
                "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8",
                "-cp", System.getProperty("java.class.path"), "psp.ut1.Mayusculas");
        pb.redirectError(new File("errores_hijo.log"));   // stderr del hijo a un fichero
        Process hijo = pb.start();

        // 1) Escribir en la entrada estándar del hijo
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(hijo.getOutputStream(), StandardCharsets.UTF_8))) {
            bw.write("hola desde el proceso padre");
            bw.newLine();
            bw.write("programación de servicios y procesos");
            bw.newLine();
        }   // al cerrar el flujo, el hijo recibe el fin de datos

        // 2) Leer la salida estándar del hijo
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(hijo.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("El hijo responde: " + linea);
            }
        }

        // 3) Esperar a que termine y consultar su código de salida
        int codigo = hijo.waitFor();
        System.out.println("El hijo ha terminado con código " + codigo);
    }
}
