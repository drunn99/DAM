/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio7restaurante;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author drunn
 */
public class GestionRestaurante {
    
    private ArrayList<Restaurante> listaRestaurantes = new ArrayList<Restaurante>();
    
    public String comprobarRuta (String ruta) {
        File localFile = new File(ruta);

        if(localFile.exists()) {
            if (localFile.isDirectory()) {
                return("Es un directorio");
            } else {
                return(
                        "Es un fichero: \n" +
                                "Nombre: " + localFile.getName() + "\n" +
                                "Tamaño en bytes: " + localFile.length() + "\n" +
                                "Permisos: Escritura " + localFile.canWrite() +
                                " | Lectura " + localFile.canRead()
                );
            }
        } else {
            return("El archivo no existe");
        }
    }
    
    public void leerFicheroRestaurantes (String ruta) throws FileNotFoundException {
        File localFile = new File(ruta);
        listaRestaurantes.clear();

        if(localFile.isFile() && localFile.canRead()) {
            try (BufferedReader br = new BufferedReader(new FileReader(localFile))) {
                
                String newLine = newLine = br.readLine(); //Directamente insertamos la cabecera, así la ignorará en el bucle
                
                while((newLine = br.readLine()) != null) {
                    listaRestaurantes.add(fromStringData(newLine));
                }
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public ArrayList<Restaurante> mostrarCp2(String ruta) throws FileNotFoundException{
        leerFicheroRestaurantes(ruta);
        
        ArrayList<Restaurante> listaFiltrada = new ArrayList<Restaurante>();
        
        for (Restaurante restaurante : listaRestaurantes) {
            if(restaurante.getCp().charAt(0) == '2') {
                listaFiltrada.add(restaurante);
            }
        }
        
        return listaFiltrada;
    }
    
    public String addRestaurante (String ruta, Restaurante newRestaurante) {
        File fichero = new File(ruta);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero,true))) {
            bw.append(newRestaurante.toString());
            return "Se ha añadido correctamente el restaurante";
        } catch (Exception ex) {
            return "Ha ocurrido un error al añadir el restaurante: " + ex.getMessage();
        }
    }
    
    public int copyWithoutCp2 () {
        File newFile = new File(".\\files\\Restaurants2.csv");
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))) {
            
            bw.append("Restaurante;Direccion;Ciudad;Provincia;CP\n");
            
            for (Restaurante restaurante : listaRestaurantes) {
                if(restaurante.getCp().charAt(0) != '2') {
                    bw.append(restaurante.toString() + "\n");
                }
            }

            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
    }
    
    public int deleteResFile (String ruta) {
        File deleteFile = new File(ruta);
        
        if(deleteFile.exists() && deleteFile.isFile()){
            deleteFile.delete();
            return 0; //"El fichero se ha borrado correctamente";
        } else {
            return 1; //"El fichero no existe o es un directorio";
        }
    }
    
    private Restaurante fromStringData(String stringData) {
        String [] splitData = stringData.split(";");
        return new Restaurante(splitData[0],splitData[1],splitData[2],splitData[3],splitData[4]);
    }
}
