/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio3;

import java.io.File;

/**
 *
 * @author drunn
 */
public class Cliente {
    String dni;
    String name;
    String surnames;
    String location;
    
    Cliente(String dni, String name, String surnames, String location) {
        this.dni = dni;
        this.name = name;
        this.surnames = surnames;
        this.location = location;
    }
    
    public String JoinData () {
        return String.join(";", dni, name, surnames, location);
    }
}
