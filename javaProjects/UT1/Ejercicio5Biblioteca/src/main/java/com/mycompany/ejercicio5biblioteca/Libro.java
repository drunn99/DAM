/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio5biblioteca;

/**
 *
 * @author drunn
 */
public class Libro {
    
    private String titulo;
    private String autor;
    private String editorial;
    
// <editor-fold defaultstate="collapsed" desc="Constructors">
    public Libro(String titulo, String autor, String editorial) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Getters">
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Setters">
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
// </editor-fold>

    @Override
    public String toString() {
        return String.format("%s;%s;%s;",this.titulo,this.autor,this.editorial);
    }
        
}
