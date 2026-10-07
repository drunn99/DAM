/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio5biblioteca;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author drunn
 */
public class ClubLectura {
    
    private ArrayList<Prestamo> prestamos;

    public ClubLectura() {
        this.prestamos = new ArrayList<Prestamo>();
    }
    
    public void lecturaPrestamos(File ficheroPrestamos) {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroPrestamos, StandardCharsets.UTF_8))) {
            String nextLine = "";
            
            while((nextLine = br.readLine()) != null) {
                String[] splitData = nextLine.split(";");
                Object[] objectData = getPrestamoData(splitData);
                Prestamo prestamo = new Prestamo((Libro)objectData[1],(Miembro)objectData[0],(LocalDate)objectData[2]);
                this.prestamos.add(prestamo);
            }
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Object[] getPrestamoData(String[] prestamoSplitData) {
        //Copiamos los numeros de la fecha para conformar el localDate más tarde
        String[] splittedDate = prestamoSplitData[8].split("/");
        int[] dateNumbers = new int[3]; 
        for (int i = 0; i < splittedDate.length; i++) {
            dateNumbers[i] = Integer.parseInt(splittedDate[i]);
        }

        //Conformamos objetos para pasar al constructor del prestamo.
        Object[] arrayParametros = new Object[3];
        arrayParametros[0] = new Miembro(Integer.parseInt(prestamoSplitData[0]), prestamoSplitData[1], prestamoSplitData[2], prestamoSplitData[3], prestamoSplitData[4]);
        arrayParametros[1] = new Libro(prestamoSplitData[5], prestamoSplitData[6], prestamoSplitData[7]);
        arrayParametros[2] = LocalDate.of(dateNumbers[2],dateNumbers[1],dateNumbers[0]);

        return arrayParametros;
    }
    
    public ArrayList<Prestamo> getPrestamoEditorial (String editorial) {
        
    }

    
}
