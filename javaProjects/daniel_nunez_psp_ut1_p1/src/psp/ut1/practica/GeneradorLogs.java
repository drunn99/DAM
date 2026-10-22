package psp.ut1.practica;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

/**
 * CÓDIGO BASE (no hay que modificarlo).
 * Genera ficheros de log de acceso de un servidor web ficticio para la práctica.
 *
 * Uso: java psp.ut1.practica.GeneradorLogs [carpeta] [numFicheros] [lineasPorFichero]
 * Valores por defecto: logs 12 250000
 *
 * Formato de cada línea:
 * IP - - [dd/MMM/yyyy:HH:mm:ss +0200] "MÉTODO RECURSO HTTP/1.1" ESTADO BYTES TIEMPO_MS
 * Aproximadamente el 0,2 % de las líneas se generan corruptas a propósito.
 */
public class GeneradorLogs {

    private static final String[] METODOS = {"GET", "GET", "GET", "GET", "POST", "PUT", "DELETE"};
    private static final String[] RECURSOS = {"/", "/index.html", "/productos", "/productos/", "/carrito",
            "/api/pedidos", "/api/usuarios", "/login", "/logout", "/img/logo.png", "/css/estilos.css",
            "/js/app.js", "/buscar?q=portatil", "/admin", "/api/stock"};
    private static final int[] ESTADOS = {200, 200, 200, 200, 200, 200, 200, 201, 204, 301, 304,
            400, 401, 403, 404, 404, 500, 502, 503};

    public static void main(String[] args) throws IOException {
        Path carpeta = Path.of(args.length > 0 ? args[0] : "logs");
        int numFicheros = args.length > 1 ? Integer.parseInt(args[1]) : 12;
        int lineas = args.length > 2 ? Integer.parseInt(args[2]) : 250_000;
        Files.createDirectories(carpeta);

        Random rnd = new Random(2026);
        String[] ips = new String[400];
        for (int i = 0; i < ips.length; i++) {
            ips[i] = (i < 40 ? "192.168.1." : "83." + rnd.nextInt(256) + "." + rnd.nextInt(256) + ".")
                    + (1 + rnd.nextInt(254));
        }
        DateTimeFormatter fDia = DateTimeFormatter.ofPattern("dd/MMM/yyyy", Locale.ENGLISH);
        LocalDate dia = LocalDate.of(2026, 9, 1);

        for (int f = 0; f < numFicheros; f++, dia = dia.plusDays(1)) {
            Path fichero = carpeta.resolve("access-" + dia + ".log");
            // Algunos ficheros son más grandes que otros (días con más tráfico)
            int total = (int) (lineas * (0.5 + rnd.nextDouble()));
            try (BufferedWriter bw = Files.newBufferedWriter(fichero, StandardCharsets.UTF_8)) {
                for (int i = 0; i < total; i++) {
                    if (rnd.nextInt(500) == 0) {
                        bw.write("#### linea corrupta " + rnd.nextInt());
                    } else {
                        int seg = (int) ((long) i * 86_400 / total);
                        String ip = ips[(int) Math.min(ips.length - 1, Math.abs(rnd.nextGaussian()) * 60)];
                        String rec = RECURSOS[rnd.nextInt(RECURSOS.length)];
                        if (rec.endsWith("/") && rec.length() > 1) rec += rnd.nextInt(5000);
                        int estado = ESTADOS[rnd.nextInt(ESTADOS.length)];
                        int bytes = estado == 204 || estado == 304 ? 0 : 200 + rnd.nextInt(60_000);
                        int ms = 5 + (int) Math.abs(rnd.nextGaussian() * 180);
                        bw.write(String.format(Locale.ROOT, "%s - - [%s:%02d:%02d:%02d +0200] \"%s %s HTTP/1.1\" %d %d %d",
                                ip, dia.format(fDia), seg / 3600, (seg / 60) % 60, seg % 60,
                                METODOS[rnd.nextInt(METODOS.length)], rec, estado, bytes, ms));
                    }
                    bw.newLine();
                }
            }
            System.out.printf("Generado %s (%d líneas)%n", fichero, total);
        }
    }
}
