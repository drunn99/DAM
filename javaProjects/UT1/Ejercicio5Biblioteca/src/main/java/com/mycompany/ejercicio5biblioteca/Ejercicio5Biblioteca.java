/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio5biblioteca;

import java.io.File;

/**
 *
 * @author drunn
 */
public class Ejercicio5Biblioteca {

    public static void main(String[] args) {
        ClubLectura elLector = new ClubLectura();
        File prestamos = new File(".\\files\\prestamos.txt");
        
        elLector.lecturaPrestamos(prestamos);
    }
}
