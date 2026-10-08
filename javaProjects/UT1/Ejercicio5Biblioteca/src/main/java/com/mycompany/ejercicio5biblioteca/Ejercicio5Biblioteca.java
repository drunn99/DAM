/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ejercicio5biblioteca;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author drunn
 */
public class Ejercicio5Biblioteca {

    public static void main(String[] args) throws IOException {
        File prestamosFile = new File(".\\files\\prestamos.txt");
        ClubLectura elLector = new ClubLectura(prestamosFile);

        /*
         * Hola Marian, verás un boton "Cargar demo", carga los datos que has dejado en la tarea en el fichero y en la clase directamente.
         * He dejado a mayores un formulario por practicar un poco las librerías gráficas y trastear un poco con paneles.
         */
        
        int response = -1;
        String[] options = new String[]{"Cargar fichero", "Mostrar datos", "Filtrar Editorial",
                "Añadir Prestamos", "Generar Devoluciones", "Cargar demo", "Cerrar"};

        while (response != 6) {
            response = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Ejercicio 5 - Biblioteca",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            switch (response) {
                case 0:
                    int result = 0;
                    try {
                        result = elLector.setPrestamos();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if (result == 1) {
                        JOptionPane.showOptionDialog(null, "Se han cargado los datos correctamente", "Ok",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, new String[]{"Cerrar"}, "Cerrar");
                    } else {
                        JOptionPane.showOptionDialog(null, "Error al cargar los datos", "ERROR",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, new String[]{"Cerrar"}, "Cerrar");
                    }

                    break;
                case 1:
                    if (prestamosFile.isFile() && elLector.getPrestamos().size() > 1) {
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(prestamosFile));
                            String nextLine = "";
                            String textDocument = "";

                            while ((nextLine = br.readLine()) != null) {
                                textDocument = textDocument.concat(nextLine + "\n");
                            }

                            br.close();

                            JOptionPane.showOptionDialog(null, textDocument, prestamosFile.getName(),
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, new String[]{"Cerrar"}, "Cerrar");

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showOptionDialog(null, "No hay datos cargados", "Error",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, new String[]{"Cerrar"}, "Cerrar");
                    }
                    break;
                case 2:
                	String editorial = JOptionPane.showInputDialog(null, "Introduce la editorial",
                            "Filtrar datos", JOptionPane.QUESTION_MESSAGE);
                	ArrayList<Prestamo> listaFiltrada = elLector.getPrestamoEditorial(editorial);
                    String prestamoEditorial = "";
                	for (Prestamo prestamo : listaFiltrada) {
                		prestamoEditorial = prestamoEditorial.concat(prestamo.toString() + "\n");
					}
                	
                    JOptionPane.showOptionDialog(null, prestamoEditorial, String.format("Prestamos de la editorial: %s", editorial),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, new String[]{"Cerrar"}, "Cerrar");
                	
                    break;
                case 3:
                	if (prestamosFile.isFile()) {
                        try {
                        	elLector.nuevoPrestamo();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showOptionDialog(null, "No se ha cargado el archivo de prestamos", "Error",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, new String[]{"Cerrar"}, "Cerrar");
                    }
                        
                    break;
                case 4:
                	String anio = JOptionPane.showInputDialog(null, "Introduce el año a filtrar",
                    "Filtrar datos", JOptionPane.QUESTION_MESSAGE);

                    if (anio == null) {
                        break;
                    }

                    try {

                        File devoluciones = elLector.devoluciones(anio.trim());

                        BufferedReader br = new BufferedReader(new FileReader(devoluciones));
                        String nextLine = "";
                        String textDocument = "";

                        while ((nextLine = br.readLine()) != null) {
                            textDocument = textDocument.concat(nextLine + "\n");
                        }

                        br.close();

                        JOptionPane.showOptionDialog(null, textDocument, prestamosFile.getName(),
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, new String[]{"Cerrar"}, "Cerrar");

                    } catch (NumberFormatException ex) {
                        JOptionPane.showOptionDialog(null, "El año debe ser un número", "Error",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                                null, new String[]{"Cerrar"}, "Cerrar");
                    }
                    break;
                case 5:
                    try {
                        int total = elLector.cargarDemo();
                        JOptionPane.showMessageDialog(null, "Demo cargada: " + total + " préstamos",
                                "Cargar demo", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al escribir el fichero de la demo",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        }

    }

}
