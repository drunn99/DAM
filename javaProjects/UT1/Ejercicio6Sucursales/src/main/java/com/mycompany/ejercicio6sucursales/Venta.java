/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio6sucursales;

import java.time.LocalDate;

/**
 *
 * @author drunn
 */
public class Venta {
    private String empresa;
    private LocalDate fecha;
    private float importe;

    public Venta(String empresa, LocalDate fecha, float importe) {
        this.empresa = empresa;
        this.fecha = fecha;
        this.importe = importe;
    }

    public String getEmpresa() {
        return empresa;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public float getImporte() {
        return importe;
    }
    
    @Override
    public String toString(){
        return String.format("%s,%s,%f",empresa,fecha,importe );
    }
    
    
}
