/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio6sucursales;

import java.util.ArrayList;

/**
 *
 * @author drunn
 */
public class FusionadorVentas {
    ArrayList <Sucursal> sucursales;
    ArrayList <Venta> allVentas;

    public FusionadorVentas(ArrayList<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }
    
    public void fusionarVentas() {
        for (Sucursal sucursal : sucursales) {
            allVentas.addAll(sucursal.getVentas());
        }
    }
    
    public float getImporteVentasPromedio() {
        return getImporteVentasTotal() / getVentasTotales();
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
