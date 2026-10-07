/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio5biblioteca;

/**
 *
 * @author drunn
 */
public class Miembro { 
    
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private String ciudad;
    
// <editor-fold defaultstate="collapsed" desc="Constructors">
    public Miembro(int id, String nombre, String email, String telefono, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Getters">
    public String getCiudad() {
        return ciudad;
    }
    
    public int getId() {    
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }


// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Setters">
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
// </editor-fold>
    
    @Override
    public String toString() {
        return "Miembro{" + "id=" + id + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono + ", ciudad=" + ciudad + '}';
    }
    
}
