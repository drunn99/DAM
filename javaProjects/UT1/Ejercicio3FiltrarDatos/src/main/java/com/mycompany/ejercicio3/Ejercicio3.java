/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author drunn
 */
public class Ejercicio3 {

    public static void main(String[] args) {
        //Obtener fichero
        File clientesFile = new File(".\\files\\clientes.txt");
        
        //Obtener clientes
        List<Cliente> listaClientes = getClientes(clientesFile);
        
        //Separar clientes en ficheros y obtener conteo por provincia.
        HashMap<String, Integer> countPerLocation = splitClients(listaClientes);
        
        //Imprimir conteo
        System.out.println("Clientes de Burgos: " + countPerLocation.get("Burgos"));
        System.out.println("Clientes de Soria: " + countPerLocation.get("Soria"));
    }
    
    public static List<Cliente> getClientes(File clientes) {
        List<Cliente> listaClientes = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(clientes))) {
            String nextLine = "";
            while ((nextLine = br.readLine()) != null) {
                String[] clientRawData = nextLine.split(";");
                HashMap<String,String> clientHashedData = mapClientData(clientRawData);
                listaClientes.add(new Cliente(clientHashedData.get("DNI"),clientHashedData.get("NAME"),clientHashedData.get("SURNAMES"),clientHashedData.get("LOCATION")));
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        
        return listaClientes;
    }
    
    public static HashMap<String,Integer> splitClients(List<Cliente> listaClientes) {
        List<Cliente> listaBurgos = new ArrayList<>();
        List<Cliente> listaSoria = new ArrayList<>();

        for (Cliente cliente : listaClientes) {
            if(cliente.location.equalsIgnoreCase("burgos")){
                listaBurgos.add(cliente);       
            } else if (cliente.location.equalsIgnoreCase("soria")){
                listaSoria.add(cliente);
            }
        }
        
        //Generar Fichero
        generateLocationFile(listaBurgos, "burgos");
        generateLocationFile(listaSoria, "soria");
        
        //Rellenar datos de conteo
        HashMap<String, Integer> count = new HashMap<>();
        count.put("Burgos", listaBurgos.size());
        count.put("Soria", listaSoria.size());
        
        return count;
    }
    
    public static void generateLocationFile(List<Cliente> listaClienteL, String fileName) {
        File file = new File(".\\files\\" + fileName + ".txt");
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            
            for (Cliente cliente : listaClienteL) {
                bw.write(cliente.JoinData()+ "\n");
            }
            
            bw.close();
            
        } catch (Exception ex) {
        
        }
    }
    
    public static HashMap<String,String> mapClientData(String[] clientRawData) {
        HashMap<String,String> clientHashedData = new HashMap<>();
        clientHashedData.put("DNI",clientRawData[0]);
        clientHashedData.put("NAME",clientRawData[1]);
        clientHashedData.put("SURNAMES",clientRawData[2]);
        clientHashedData.put("LOCATION",clientRawData[3]);
        
        return clientHashedData;
    } 
}
