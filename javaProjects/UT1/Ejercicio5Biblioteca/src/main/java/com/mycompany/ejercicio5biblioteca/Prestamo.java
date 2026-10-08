/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio5biblioteca;

import java.time.LocalDate;

/**
 *
 * @author drunn
 */
public class Prestamo {
    private Libro libroPrestado;
    private Miembro miembro;
    private LocalDate fechaPrestamo;

// <editor-fold defaultstate="collapsed" desc="Constructors">
    public Prestamo(Libro libroPrestado, Miembro miembro, LocalDate fechaPrestamo) {
        this.libroPrestado = libroPrestado;
        this.miembro = miembro;
        this.fechaPrestamo = fechaPrestamo;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Getters">
    public Libro getLibroPrestado() {
        return libroPrestado;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Setters">
    public void setLibroPrestado(Libro libroPrestado) {
        this.libroPrestado = libroPrestado;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

// </editor-fold>

    @Override
    public String toString() {
        return miembro.toString() + libroPrestado.toString() + fechaPrestamo.toString();
    }
    
}
