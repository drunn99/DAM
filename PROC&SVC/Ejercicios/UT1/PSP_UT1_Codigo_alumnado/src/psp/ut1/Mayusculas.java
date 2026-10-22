package psp.ut1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Proceso hijo: lee líneas de su entrada estándar hasta el fin de datos
 * y las devuelve en mayúsculas por su salida estándar.
 */
public class Mayusculas {
    public static void main(String[] args) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String linea;
        int contador = 0;
        while ((linea = entrada.readLine()) != null) {   // null = fin de la entrada
            System.out.println(linea.toUpperCase());
            contador++;
        }
        System.err.println("Líneas procesadas: " + contador);
        System.exit(0);
    }
}
