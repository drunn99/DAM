/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicioficheros;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author drunn
 */
public class EjercicioFicheros {

    public static void main(String[] args) throws IOException {
        File localDirectory = new File(".\\files\\");
        File [] files = localDirectory.listFiles();
        
        System.out.println(localDirectory.getAbsolutePath());
        
        //IMPRIMIR DATOS DEL FILE (EJERCICIOS 1-5)
        for (File file : files) {
            PrintFileProperties(file);
        }
       
        //EJERCICIO 6
        CreateDirectoryAndSubDirectory(new String[]{"UT1","Ejercicios"}, localDirectory.getAbsolutePath());
        //Ejercicio 7
        CreateDirectoryAndFiles("FICHEROS", new String[] {"TestFile1","TestFile2"}, localDirectory.getAbsolutePath());
        //Ejercicio 8
        GetDirectoryFiles();
        
        
    }
    
    private static void PrintFileProperties(File file) {
                
        System.out.println("Ruta Absoluta: " + file.getAbsolutePath());
        System.out.println("Nombre del fichero: " + file.getName());
        System.out.println("Directorio padre: " + file.getParent());
        System.out.println("Última modificación: " + DateFormatter(file.lastModified()));
        System.out.println("Tamaño: " + file.length() + " bytes");
        System.out.println(file.exists() ? "El Fichero existe" : "El Fichero no existe");
        System.out.println(file.canRead() ? "El Fichero se puede leer" : "El Fichero no se puede leer");
        System.out.println(file.canWrite() ? "El Fichero se puede escribir" : "El Fichero no se puede escribir");
        System.out.println(file.canWrite() ? "El Fichero se puede escribir" : "El Fichero no se puede escribir");
        System.out.println(file.isDirectory() ? "Es un Directorio" : "Es un fichero");
    }      
    
    private static void CreateDirectoryAndSubDirectory(String [] dirName, String path){
        System.out.println(String.format("--- Ejercicio 6 --- \n Creando el directorio"));
        String completePath = path;
        
        //Recoger hilo de ficheros
        for (String directory : dirName) {
            completePath = completePath.concat(String.format("/%s", directory));
        }
        
        //Crear directorio y subdirectorios
        File newDirectory = new File(completePath);
        System.out.println(completePath);
        newDirectory.mkdirs();
        
        System.out.println("----");
    }
    
    private static void CreateDirectoryAndFiles(String directory, String [] files, String path) throws IOException{
        System.out.println(String.format("--- Ejercicio 7 --- \n Creando el directorio /%s en la ruta %s",files[0], path));
        //Crear Directorio
        String completePath = String.format("%s/%s",path, directory);
        File newDirectory = new File(completePath);
        newDirectory.mkdir();
        
        //Crear ficheros
        for (String fileName : files) {
            File newFile = new File(newDirectory.getAbsolutePath().concat(String.format("/%s",fileName)));
            newFile.createNewFile();
        }
        
        File [] generatedFiles = newDirectory.listFiles();
        
        //Renombrar uno de ellos aleatoriamente
        if(generatedFiles.length > 0) {
            Random r = new Random();
            int fileIndex = r.nextInt(0, generatedFiles.length);
            File fileRename = new File(generatedFiles[fileIndex].getAbsolutePath().concat("/renameFile.txt"));
            generatedFiles[fileIndex].renameTo(fileRename);
        } else {
            System.out.println("No se ha creado correctamente el directorio o los ficheros");
        }
        
        //Borrar uno de ellos aleatoriamente
        if(generatedFiles.length > 0) {
            Random r = new Random();
            int fileIndex = r.nextInt(0, generatedFiles.length);
            generatedFiles[fileIndex].delete();
        } else {
            System.out.println("No se ha borrado correctamente uno de los ficheros");
        }
        
        System.out.println("----");
    }
    
    private static void GetDirectoryFiles() {
        System.out.println("--- Ejercicio 8 ---");
        
        //PEDIMOS NOMBRE DE CARPETA
        Scanner sc = new Scanner(System.in);
        String directory = sc.nextLine();
        
        //Obtenemos directorio
        File searchedDirectory = new File(String.format(".\\files\\%s", directory));
        
        //Si existe direcotrio recorremos sus files, si no es directorio, imprimimos nombre
        if(searchedDirectory.exists()){
            File [] fileList = searchedDirectory.listFiles();
            
            for (File file : fileList) {
                if (!file.isDirectory()) {
                    System.out.println(file.getName());
                }
            }
        }

        
        sc.close();
    }
    
    
    private static String DateFormatter(long epochTime) {
        ZonedDateTime dateTime = Instant.ofEpochMilli(epochTime).atZone(ZoneId.of("Europe/Madrid"));
        String formatted = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return formatted;
    }
}



