package psp.ut1.practica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AnalizadorLogs {
    //Leer las rutas de la entrada estándar.
    //Analizar un fichero.
    //Analizar una línea.
    //Escribir los resultados siguiendo el protocolo.
    //Decidir el código de salida.
    public static void main(String[] args) {
        int slowThreshold = getSlowThresholdWithError(args);
        ArrayList<String> rutasFicheros = getFilesPathFromInput();

    }

    private static int getSlowThresholdWithError(String[] args) {
        try {
            if(args.length == 2 && args[0].equalsIgnoreCase("--lenta") && Integer.parseInt(args[1]) >= 0 ) {
                return Integer.parseInt(args[1]);
            } else {
                exitWithCodeAndMessage(1,"Argumentos incorrectos (falta --lenta, el umbral no es un número o es negativo...).");
                return -1;
            }
        } catch (NumberFormatException e) {
            exitWithCodeAndMessage(1,"Argumentos incorrectos (falta --lenta, el umbral no es un número o es negativo...).");
            return -1;
        }
    }

    private static ArrayList<String> getFilesPathFromInput() {
        ArrayList<String> filesPath = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int loops = 0;
            String nextLine = "";
            while ((nextLine = br.readLine()) != null)  {
                loops++;
                filesPath.add(nextLine);
            }

            if(loops == 0) {
                exitWithCodeAndMessage(3, "No se ha recibido ninguna ruta por la entrada estándar.");
            }

            return filesPath;

        } catch (IOException e) {
            exitWithCodeAndMessage(3, "No se ha recibido ninguna ruta por la entrada estándar.");
            return filesPath;
        }
    }

    private static void exitWithCodeAndMessage (int code, String message) {
        System.err.println(ProcessHandle.current().pid() + " - " + message);
        System.exit(code);
    }
}
