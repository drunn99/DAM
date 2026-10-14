/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio6sucursales;

import java.io.File;

/**
 *
 * @author drunn
 */
public class Ejercicio6Sucursales {

    public static void main(String[] args) {
        Sucursal mad = new Sucursal("Madrid", new File(".\\data\\Madrid.txt"));
        mad.getDatosFichero();
        mad.sortVentas();
        
        Sucursal bcn = new Sucursal("Barcelona", new File(".\\data\\Barcelona.txt"));
        bcn.getDatosFichero();
        bcn.sortVentas();
    }
}
