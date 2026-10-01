/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejerciciobufferedreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author drunn
 */
public class EjercicioBufferedReader {

    public static void main(String[] args) {
        //Descomenta y ejecuta el ejercicio que quieras Marian, debería haber hecho un menú, lo siento.
        //Ejercicio1A(s);
        Ejercicio1B();
    }
    
    public static void Ejercicio1A() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Introduce el nombre del fichero de números: ");
        String fileName = sc.nextLine();
        
        File numberFiles = new File (String.format(".\\files\\%s.txt", fileName));
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(numberFiles));
            String line = "";
            int count = 0;
            double avg = 0.0d;
            double sum = 0.0d;
            
            while ((line = br.readLine()) != null) {
                sum += Double.parseDouble(line);
                count ++;
            }
            
            avg = sum / count;
            
            System.out.println(String.format("La media de los números del documento es: %.1f \nLa suma de los números del documento es: %.1f", avg, sum));
                
            br.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        sc.close();
        
    }
    
    public static void Ejercicio1B() {
        int response = -1;
        String[] options = new String[] {"Crear Fichero", "Mostrar fichero", "Borrar fichero", "Cerrar"};
        
        File localDirectory = new File(".\\files");
        File newFile = new File ("");
        
        //Bucle infinito mientras no se desee cerrar
        while (response != 3) {
            response = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Ejercicio 1B",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
            
            //Control de estados
            switch (response) {
                case 0:
                    //Crear
                    String fileName = JOptionPane.showInputDialog("Introduce el nombre del fichero");
                    newFile = new File(localDirectory.getAbsolutePath().concat(String.format("/%s.txt",fileName)));
                    
                    //Recoger datos
                    String[] userData = new String[3];
                    userData[0] = JOptionPane.showInputDialog("Introduce tu nombre");
                    userData[1] = JOptionPane.showInputDialog("Introduce tu apellido");
                    userData[2] = JOptionPane.showInputDialog("Introduce tu fecha de nacimiento");
                    
                    //Abrir buffer y escribir en fichero
                    try {
                        FileWriter fr = new FileWriter(newFile);
                        for (String data : userData) {
                            fr.write(data + "\n");
                        }
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    //Mostrar
                    if(newFile.isFile()) {
                        //Mostrar fichero creado
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(newFile));
                            String nextLine = "";
                            String textDocument = "";
                            
                            while((nextLine = br.readLine()) != null) {
                                textDocument = textDocument.concat(nextLine + "\n");
                            }
                            
                            br.close();
                            
                            JOptionPane.showOptionDialog(null, textDocument , newFile.getName(),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, new String[]{"Cerrar"} , "Cerrar");
                            
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        JOptionPane.showOptionDialog(null, "Por favor, genere un fichero primero" , "Error",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, new String[]{"Cerrar"} , "Cerrar");
                    }
                    break;
                    
                case 2:
                    //Borrar
                    if(newFile.isFile()) {
                        //Borrar fichero creado
                        newFile.delete();

                    } else {
                        JOptionPane.showOptionDialog(null, "Por favor, genere un fichero primero" , "Error",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, new String[]{"Cerrar"} , "Cerrar");
                    }
                    break;
            }
        }

    }
}
