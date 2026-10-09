/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio6sucursales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author drunn
 */
public class Sucursal {
    private String nombre;
    private ArrayList<Venta> ventas;
    private File ficheroSucursal;

    public Sucursal(String nombre,File ficheroSucursal) {
        this.ficheroSucursal = ficheroSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public File getFicheroSucursal() {
        return ficheroSucursal;
    }
    

    public int getDatosFichero() {
        try(BufferedReader br = new BufferedReader(new FileReader(ficheroSucursal))) {
            String nextLine = "";
            while((nextLine = br.readLine()) != null) {
                ventas.add(parseStringData(nextLine.split(";")));
            }
        } catch (Exception ex){
            
        }
    }
    
    private Venta parseStringData (String [] splitData) {
        Venta newVenta = new Venta (
                splitData[0],
                splitData[1],
                splitData[3]
        );
    }
    
    
    
}
