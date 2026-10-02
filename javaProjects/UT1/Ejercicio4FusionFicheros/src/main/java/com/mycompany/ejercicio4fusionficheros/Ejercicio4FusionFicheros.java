/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio4fusionficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author drunn
 */
public class Ejercicio4FusionFicheros {

    public static void main(String[] args) {
        //Leer los ficheros de la carpeta, generar lista de juegos
    	File gameWhDirectory = new File(".\\files\\");
    	List<Juego> listaJuegos = new ArrayList<>();
    	obtenerJuegos(gameWhDirectory, listaJuegos);
    	
        //Usar lista de juegos, ordenar por id.
    	listaJuegos.sort(Comparator.comparingInt(j -> j.id));
    	
        //Generar fichero con la lista final
        generateFile(listaJuegos);
    }
    
    public static void obtenerJuegos(File directoryOrFile, List<Juego> listaJuegos){
    	if(directoryOrFile.isDirectory()) {
        	File[] files = directoryOrFile.listFiles();
        	for (File file : files) {
    			if (file.getName().contains("Almacen")) {
    				//Llamada recursiva para obtener la lista de juegos de este fichero, acumular en variable y retornar al final
    				obtenerJuegos(file, listaJuegos);
    			}
    		}
        	
    	} else if (directoryOrFile.isFile()) {
    		try (BufferedReader br = new BufferedReader(new FileReader(directoryOrFile))) {
    			String nextLine = "";
    			
    			while((nextLine = br.readLine()) != null){
    				HashMap<String,String> gameHashedData = mapGameData(nextLine);
    				Juego nuevoJuego = new Juego(
    						Integer.parseInt(gameHashedData.get("ID"))
    						,gameHashedData.get("TITULO")
    						,Float.parseFloat(gameHashedData.get("PRECIO")));
    						
    				if(!listaJuegos.contains(nuevoJuego)) {
    					listaJuegos.add(nuevoJuego);
    				}
    				
    			}
    			
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		};
    	}

    }
    
    public static HashMap<String, String> mapGameData(String gameRawData){
    	HashMap<String, String> gameHashedData = new HashMap<>();
    	String[] gameSplitData = gameRawData.split(";");
    	
    	gameHashedData.put("ID", gameSplitData[0]);
    	gameHashedData.put("TITULO", gameSplitData[1]);
    	gameHashedData.put("PRECIO", gameSplitData[2]);
    	
    	return gameHashedData;
    }
    
    public static void generateFile(List<Juego> listaJuegos){
    	File catalogoCompleto = new File(".\\files\\CatalogoCompleto.txt");
    	
    	try (BufferedWriter bw = new BufferedWriter(new FileWriter(catalogoCompleto))) {
    		//catalogoCompleto.createNewFile();
    		for (Juego juego : listaJuegos) {
				String juegoData = juego.joinData() + "\n"; 
				bw.write(juegoData);
			}
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
    	
    }
}
