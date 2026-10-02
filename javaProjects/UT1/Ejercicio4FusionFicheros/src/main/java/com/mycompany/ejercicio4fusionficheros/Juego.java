/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio4fusionficheros;

/**
 *
 * @author drunn
 */
public class Juego {
    int id;
    String titulo;
    float precio;
    
    Juego(int id, String titulo, float precio){
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Juego)) return false;
        return id == ((Juego) o).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    
    public String joinData() {
    	return String.join(";",String.valueOf(id),titulo,String.valueOf(precio));
    }
    
    
}
