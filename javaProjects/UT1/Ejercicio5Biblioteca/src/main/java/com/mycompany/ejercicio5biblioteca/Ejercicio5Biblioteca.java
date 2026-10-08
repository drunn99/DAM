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
        ClubLectura elLector = new ClubLectura();

        int response = -1;
        String[] options = new String[]{"Cargar fichero", "Mostrar datos", "Filtrar datos", "Añadir Prestamos", "Generar Devoluciones", "Cerrar"};

        while (response != 5) {
            response = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Ejercicio 5 - Biblioteca",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            switch (response) {
                case 0:
                    int result = 0;
                    try {
                        result = elLector.setPrestamos(prestamosFile);
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
                case 3:
                        elLector.nuevoPrestamo();
                    break;
                case 4:

                    break;
                default:
            }
        }

    }

}
