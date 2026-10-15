/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio6sucursales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
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
        this.nombre = nombre;
        this.ficheroSucursal = ficheroSucursal;
        this.ventas = new ArrayList<Venta>();
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
    
    public String getNombreSucursal() {
        return ficheroSucursal.getName();
    }
    
    public int getNumeroVentas() {
        return  ventas.size();
    }
    
    public float getImporteVentasTotal() {
        float totalVentas = 0.0f;
        for (Venta venta : ventas) {
            totalVentas += venta.getImporte();
        }
        
        return totalVentas;
    }
    
    public float getImporteVentasPromedio() {
        return getImporteVentasTotal() / getNumeroVentas();
    }

    public int getDatosFichero() {
        try(BufferedReader br = new BufferedReader(new FileReader(ficheroSucursal))) {
            String nextLine = "";
            while((nextLine = br.readLine()) != null) {
                ventas.add(parseStringData(nextLine.split(";")));
            }
            return 1;
        } catch (Exception ex){
            return 0;
        }
    }
    
    public void addVenta(Venta newVenta) {
        ventas.add(newVenta);
    }
    
    public void sortVentas() {
        ventas.sort((o1, o2) -> o1.getFecha().compareTo(o2.getFecha()));
    }
    
    private Venta parseStringData (String [] splitData) {
        String [] splitDate = splitData[1].split("/");
        int year = Integer.parseInt(splitDate[2]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[0]);
        Venta newVenta = new Venta (
                splitData[0],
                LocalDate.of(year,month,day),
                Float.parseFloat(splitData[2])
        );
        
        return newVenta;
    }
    
    
    
}
