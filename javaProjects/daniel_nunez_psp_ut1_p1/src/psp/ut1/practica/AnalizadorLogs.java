package psp.ut1.practica;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorLogs {
    //Analizar un fichero.
    //Analizar una línea.
    //Escribir los resultados siguiendo el protocolo.
    //Decidir el código de salida.
    public static void main(String[] args) {
        String exitMsg = "Ejecución correcta: se han analizado todos los ficheros recibidos.";
        int exitCode = 0;

        int slowThreshold = getSlowThresholdWithError(args);
        ArrayList<String> rutasFicheros = getFilesPathFromInput();
        ArrayList<String> results = getFileData(rutasFicheros, slowThreshold);

        for (String str : results) {
            if (str.contains("ERROR") && exitCode == 0) {
                exitCode = 2;
                exitMsg = "Algún fichero no se ha podido leer. El resto se han analizado y sus resultados son válidos.";
            }
            System.out.println(str);
        }


        exitWithCodeAndMessage(exitCode,exitMsg);
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
                if(nextLine.trim().length() != 0){
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

    private static ArrayList<String> getFileData(ArrayList<String> filesPath, int slowThreshold) {
        ArrayList<Result> results = new ArrayList<Result>();
        for (String path : filesPath) {
            File log = new File(path);
            if(log.exists() && log.isFile()) {
                try(BufferedReader br = new BufferedReader(new FileReader(log))) {

                        Result currentLogResult = new Result(log.getName(), log.getAbsolutePath());
                        String nextLine;

                        while((nextLine = br.readLine()) != null) {
                            currentLogResult.processLine(nextLine, slowThreshold);
                        }

                        results.add(currentLogResult);

                } catch (Exception e) {
                    results.add(new Result("",path, true));
                }
            } else {
                results.add(new Result("",path, true));
            }
        }

        ArrayList<String> resultsText = new ArrayList<String>();
        resultsText.addAll(getOKFilesResult(results));
        resultsText.addAll(getInvalidFilesResult(results));
        resultsText.addAll(getTotalFilesResult());
        resultsText.addAll(getIpAndResponseSummary());
        resultsText.add("FIN");

        return resultsText;
    }

    private static ArrayList<String> getOKFilesResult(ArrayList<Result> filesResult) {
        ArrayList<String> resultsToText = new ArrayList<String>();
        for (Result res : filesResult) {
            if (!res.isFailed()) {
                String toText = String.format("FICHERO;%s;%d;%d",res.getFileName(), res.fileTotalLines, res.fileTotalInvalidLines);
                resultsToText.add(toText);
            }
        }

        return resultsToText;
    }

    private static ArrayList<String> getInvalidFilesResult(ArrayList<Result> filesResult) {
        ArrayList<String> resultsToText = new ArrayList<String>();
        for (Result res : filesResult) {
            if (res.isFailed()) {
                String toText = String.format("ERROR;%s",res.getFilePath());
                resultsToText.add(toText);
            }
        }

        return resultsToText;
    }

    private static ArrayList<String> getTotalFilesResult() {
        ArrayList<String> resultsToText = new ArrayList<String>();

        resultsToText.add(String.format("TOTAL;%d;%d", Result.filesTotalLines,Result.filesTotalInvalidLines));
        resultsToText.add(String.format("BYTES;%d",Result.filesBytesTotal));
        resultsToText.add(String.format("LENTAS;%d",Result.filesTotalSlowCalls));

        return resultsToText;
    }

    private static ArrayList<String> getIpAndResponseSummary(){
        ArrayList<String> resultsToText = new ArrayList<String>();

        for (Map.Entry<String, Integer> entry : Result.globalResponseCounter.entrySet()){
            resultsToText.add(String.format("ESTADO;%s;%d",entry.getKey(),entry.getValue()));
        }

        for (Map.Entry<String, Integer> entry : Result.globalIpCounter.entrySet()){
            resultsToText.add(String.format("IP;%s;%d",entry.getKey(),entry.getValue()));
        }

        return resultsToText;
    }

    private static void exitWithCodeAndMessage (int code, String message) {
        System.err.println(ProcessHandle.current().pid() + " - " + message);
        System.exit(code);
    }

}

class Result {
    public static long filesTotalLines = 0L;
    public static int filesTotalInvalidLines = 0;
    public static int filesTotalSlowCalls = 0;
    public static long filesBytesTotal = 0;
    public static HashMap<String, Integer> globalIpCounter = new HashMap<String, Integer>();
    public static HashMap<String, Integer> globalResponseCounter = new HashMap<String, Integer>();

    public long fileTotalLines = 0L;
    public int fileTotalInvalidLines = 0;
    public int fileSlowCalls = 0;
    public long bytesTotal = 0;

    private String fileName;
    private String filePath;
    private boolean isFailed = false;

    private final Pattern regExRequest = Pattern.compile(
            "^((?:[0-9]{1,3}\\.){3}[0-9]{1,3})\\s+\\-\\s+\\-\\s+\\[(.*?)\\]\\s+\\\"(.*?)\\\"" +
                    "\\s+(\\d{3})\\s+(\\d+)\\s+(\\d+)$"
    );

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

    //region Getters
    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isFailed() {
        return isFailed;
    }
    //endregion

    //region Methods
    public void processLine(String line, int slowThreshold) {
        Matcher matcher = regExRequest.matcher(line);

        if (matcher.matches()) {
            String ip = matcher.group(1);
            globalIpCounter.put(ip, globalIpCounter.getOrDefault(ip, 0) + 1);

            String statusCode = matcher.group(4);
            int bytes = Integer.parseInt(matcher.group(5));
            int timeMs = Integer.parseInt(matcher.group(6));

            processResponse(statusCode, bytes, timeMs, slowThreshold);
        } else {
            fileTotalInvalidLines++;
            filesTotalInvalidLines++;
        }

        fileTotalLines++;
        filesTotalLines++;
    }

    private void processResponse(String statusCode, int bytes, int msCounter, int slowThreshold) {
        globalResponseCounter.put(statusCode, globalResponseCounter.getOrDefault(statusCode, 0) + 1);

        this.bytesTotal += bytes;
        filesBytesTotal += bytes;

        if (msCounter > slowThreshold) {
            fileSlowCalls++;
            filesTotalSlowCalls++;
        }
    }
    //endregion
}
