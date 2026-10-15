/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio7restaurante;

/**
 *
 * @author drunn
 */
public class Restaurante {
    private String restaurante;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String cp;

    public Restaurante(String restaurante,String direccion, String ciudad, String provincia, String cp) {
        this.restaurante = restaurante;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.cp = cp;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCp() {
        return cp;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s", restaurante,direccion,ciudad,provincia,cp);
    }
    
    
}
