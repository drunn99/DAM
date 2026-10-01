/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio2fichero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author drunn
 */
public class Ejercicio2Fichero {

    public static void main(String[] args) {
        //Mostrar menú
        int response = -1;
        String[] options = new String[] {"Crear Fichero", "Mostrar fichero", "Cerrar"};
        
        File localDirectory = new File(".\\files");
        File newFile = new File ("");
        
        //Bucle infinito mientras no se desee cerrar
        while (response != 2) {
            response = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Ejercicio 2 - Números pares",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
            
            switch (response) {
                case 0:
                    //Crear fichero de números
                    String fileName = JOptionPane.showInputDialog("Introduce el nombre del fichero");
                    newFile = new File(localDirectory.getAbsolutePath().concat(String.format("/%s.txt",fileName))); 
                    
                    int[] evenNumbers = new int[100];
                    for(int i = 0; i < 100; i++){
                        evenNumbers[i] = i*2;
                    }
                    
                    //Abrir buffer y escribir en fichero
                    try {
                        FileWriter fr = new FileWriter(newFile);
                        for (int evenNumber : evenNumbers) {
                            fr.write(evenNumber + " | ");
                            if(evenNumber != 0 && evenNumber % 10 == 0)
                                fr.write("\n");
                        }
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    //Mostrar fichero
                    if(newFile.isFile()) {
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
            }
            
        }
       
    }
}
