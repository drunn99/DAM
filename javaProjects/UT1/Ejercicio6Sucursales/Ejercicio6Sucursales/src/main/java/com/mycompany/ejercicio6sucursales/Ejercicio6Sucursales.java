/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio6sucursales;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author drunn
 */
public class Ejercicio6Sucursales {

    public static void main(String[] args) {
    	try {
            Sucursal mad = new Sucursal("Madrid", new File(".\\data\\Madrid.txt"));
            mad.getDatosFichero();
            mad.sortVentas();
            
            Sucursal bcn = new Sucursal("Barcelona", new File(".\\data\\Barcelona.txt"));
            bcn.getDatosFichero();
            bcn.sortVentas();
            
            FusionadorVentas fusion = new FusionadorVentas(new ArrayList<Sucursal>(List.of(mad,bcn)));
            fusion.generarFicheroFusionado();
            
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	

        
    }
}
