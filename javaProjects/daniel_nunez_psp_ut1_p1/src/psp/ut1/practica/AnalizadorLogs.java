package psp.ut1.practica;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorLogs {
    //Analizar un fichero.
    //Analizar una línea.
    //Escribir los resultados siguiendo el protocolo.
    //Decidir el código de salida.
    public static void main(String[] args) {
        int slowThreshold = getSlowThresholdWithError(args);
        ArrayList<String> rutasFicheros = getFilesPathFromInput();
        ArrayList<String> results = getFileData(rutasFicheros);


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
            String nextLine;
            while ((nextLine = br.readLine()) != null)  {
                loops++;
                if(nextLine.trim().length() == 0){
                    filesPath.add(nextLine);
                }
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

    private static ArrayList<String> getFileData(ArrayList<String> filesPath) {
        ArrayList<Result> results = new ArrayList<Result>();
        for (String path : filesPath) {
            //Abrir ruta de fichero
            try {
                File log = new File(path);
                if(log.exists() && log.isFile()) {
                    //Leer fichero linea a linea
                    //Pasarlo a result.processLine() para que suma y añada valores a sus variables locales y globales
                    //Añadir objeto result a arrayList de resultados
                    BufferedReader br = new BufferedReader(new FileReader(log));
                    Result currentLogResult = new Result(log.getName(), log.getAbsolutePath());
                    String nextLine;

                    while((nextLine = br.readLine()) != null) {
                        currentLogResult.processLine(nextLine);
                    }

                } else {
                    results.add(new Result("",path, true));
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
            //Componer retornos por fichero desde arraylist de objetos results
            //Añadir retornos globales (Los de una sola vez + suma por ip y código)
            //Añadir retorno FIN
    }

    private static void exitWithCodeAndMessage (int code, String message) {
        System.err.println(ProcessHandle.current().pid() + " - " + message);
        System.exit(code);
    }
}

class Result {
    static long filesTotalLines = 0L;
    static int filesTotalInvalidLines = 0;
    static int filesTotalSlowCalls = 0;
    static long filesBytesTotal = 0;
    static HashMap<String, Integer>  globalIpCounter = new HashMap<String, Integer>();
    static HashMap<String, Integer>  globalResponseCounter = new HashMap<String, Integer>();

    long fileTotalLines = 0L;
    int fileTotalInvalidLines = 0;
    int fileSlowCalls = 0;
    long bytesTotal = 0;

    String fileName;
    String filePath;
    boolean isFailed = false;

    final String end = "FIN";

    //192.168.1.30 - - [01/Sep/2026:10:15:32 +0200] "GET /img/logo.png HTTP/1.1" 200 5320 87
    //^(?:[0-9]{1,3}\.){3}[0-9]{1,3} para ip
    //\[(.*?)\] para la fecha
    //\"(.*?)\" para el metodo
    final Pattern regExIP = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}");
    final Pattern regExStatus = Pattern.compile("[[:blank:]]\\d{3}[[:blank:]]\\d{0,12}[[:blank:]]\\d{0,6}");
    final Pattern regExRequest = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}[[:blank:]]\\-[[:blank:]]\\-[[:blank:]]\\[(.*?)\\][[:blank:]]\"(.*?)\\\"([[:blank:]]\\d{3}[[:blank:]]\\d{0,12}[[:blank:]]\\d{0,6})$");

    //region Constructor

    public Result(String fileName, String filePath, boolean isFailed) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.isFailed = isFailed;
    }

    public Result(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    //endregion

    public void processLine (String line, int slowThreshold) {
        if (line.matches(regExRequest.pattern())){
            Matcher ipMatcher = regExIP.matcher(line);
            if (ipMatcher.find()) {
                int ipCounter = globalIpCounter.get(ipMatcher.group(1)) != null ? globalIpCounter.get(ipMatcher.group(1)) : 0;
                globalIpCounter.put(ipMatcher.group(1), ipCounter + 1);
            }

            String [] splitData = line.split(regExStatus.pattern())[0].split(" ");
            if (splitData.length == 3) {
                processResponse(splitData,slowThreshold);
            } else {
                fileTotalInvalidLines++;
                filesTotalInvalidLines++;
            }
        } else {
            fileTotalInvalidLines++;
            filesTotalInvalidLines++;
        }
    }

    private void processResponse(String [] response, int slowThreshold) {
        int responseCounter = globalResponseCounter.get(response[0]) != null ? globalResponseCounter.get(response[0]) : 0;
        int bytesCounter = Integer.parseInt(response[1]);
        int msCounter = Integer.parseInt(response[2]);



    }
}
