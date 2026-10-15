/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio6sucursales;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 *
 * @author drunn
 */
public class FusionadorVentas {
    ArrayList <Sucursal> sucursales;
    ArrayList <Venta> allVentas = new ArrayList<Venta>();
    File ficheroConjunto;

    public FusionadorVentas(ArrayList<Sucursal> sucursales) {
        this.sucursales = sucursales;
        ficheroConjunto = new File(".\\data\\ventas_fusionadas.txt");
        fusionarVentas();
    }
    

    
    public void generarFicheroFusionado() {
    	try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroConjunto))) {
        	for (Sucursal sucursal : sucursales) {
        		String sucursalTitle = String.format(" ================================== \nVENTAS SUCURSAL %s \n ==================================\n",sucursal.getNombre());
        		bw.append(sucursalTitle);
    			for (Venta venta : sucursal.getVentas()) {
    				bw.append(venta.toString() + "\n");
    			}
    			String resumenTotal = 
    					String.format("-----------------\n"
    							+ "Número de ventas: %s\n"
    							+ "Importe Total: %.2f€\n"
    							+ "Promedio de ventas: %.2f\n",
    							sucursal.getNumeroVentas(),
    							sucursal.getImporteVentasTotal(),
    							sucursal.getImporteVentasPromedio());
    			
    			bw.append(resumenTotal);
    		}
        	String resumenGlobalHeader = String.format("================================== \nRESUMEN GLOBAL \n ==================================\n");
        	String resumenGlobalData = String.format("\nNúmero total de ventas: %s\n"
        			+ "Importe Total: %.2f€\n"
        			+ "Promedio de ventas global: %.2f",
        			getVentasTotales(),
        			getImporteVentasTotal(),
        			getImporteVentasPromedio());
        	bw.append(resumenGlobalHeader+resumenGlobalData);
        	
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }   
    
    private float getImporteVentasPromedio() {
        return getImporteVentasTotal() / getVentasTotales();
    }
    
    private void fusionarVentas() {
        for (Sucursal sucursal : sucursales) {
            allVentas.addAll(sucursal.getVentas());
        }
    }
    
    private float getImporteVentasTotal() {
        float importeVentasTotales = 0.0f;
        for (Sucursal sucursal : sucursales) {
            importeVentasTotales+= sucursal.getImporteVentasTotal();
        }
        
        return importeVentasTotales;
    }
    
    private int getVentasTotales() {
        int numeroVentas = 0;
        for (Sucursal sucursal : sucursales) {
            numeroVentas+= sucursal.getNumeroVentas();
        }
        
        return numeroVentas;
    }
    
    
}
