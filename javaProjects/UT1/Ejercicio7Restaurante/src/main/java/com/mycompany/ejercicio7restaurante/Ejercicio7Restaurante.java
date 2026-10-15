/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio7restaurante;

import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author drunn
 */
public class Ejercicio7Restaurante {

    public static void main(String[] args) throws FileNotFoundException {
        String defaultPath = ".\\files\\Restaurants.csv";
        GestionRestaurante gestion = new GestionRestaurante();
        gestion.leerFicheroRestaurantes(defaultPath);

        int response = -1;

        String[] options = {
                "Consultar ruta",
                "Restaurantes CP 2",
                "Añadir restaurante",
                "Crear Restaurants2.csv",
                "Borrar fichero",
                "Salir"
        };

        while (response != 5) {
            response = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Ejercicio 7 - Restaurantes",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            switch (response){
                case 0: //Consultar Ruta
                    String ruta = pedirTexto("Introduzca la ruta", defaultPath);

                    if (ruta != null) {
                        mostrarTexto("Información de la ruta", gestion.comprobarRuta(ruta));
                    }

                    break;
                case 1: //Filtrar restaurantes Codigo postal 2
                    String resFiltrados = "";
                    for(Restaurante res : gestion.mostrarCp2(defaultPath)){
                        resFiltrados+= (res.toString() + "\n");
                    }

                    mostrarTexto("Filtrados, Código Postal 2...", resFiltrados);

                    break;
                case 2: //Add restaurante
                    Restaurante newRestaurante = pedirRestaurante();
                    gestion.addRestaurante(defaultPath, newRestaurante);
                    break;
                case 3: //Crear fichero restaurantes filtrados
                    int resultado;

                    if ((resultado = gestion.copyWithoutCp2()) == 0) {
                        mostrarAviso("Correcto","Se ha copiado correctamente los restaurantes filtrados");
                    } else {
                        mostrarAviso("Error","Error durante la copia del contenido");
                    }
                    break;
                case 4: //Borrar Fichero
                    String rutaDel = pedirTexto("Introduzca la ruta", defaultPath);
                    int resultadoDel;

                    if (rutaDel != null && (resultadoDel = gestion.deleteResFile(rutaDel)) == 0) {
                        mostrarAviso("Correcto","El fichero se ha borrado.");
                    } else {
                        mostrarAviso("ERROR","El fichero no existe o es un directorio");
                    }

                    break;

            }

        }
    }

    private static String pedirTexto(String mensaje, String valorInicial) {
        Object respuesta = JOptionPane.showInputDialog(null, mensaje, "Restaurantes",
                JOptionPane.QUESTION_MESSAGE, null, null, valorInicial);
        if (respuesta == null || respuesta.toString().trim().isEmpty()) {
            return null;
        }
        return respuesta.toString().trim();
    }

    private static void mostrarTexto(String titulo, String texto) {
        JTextArea area = new JTextArea(texto == null || texto.isEmpty() ? "(sin resultados)" : texto);
        area.setEditable(false);
        area.setRows(Math.min(20, Math.max(3, area.getLineCount())));
        area.setColumns(55);
        JOptionPane.showMessageDialog(null, new JScrollPane(area), titulo, JOptionPane.PLAIN_MESSAGE);
    }

    private static void mostrarAviso(String titulo, String texto) {
        JOptionPane.showOptionDialog(null, texto, titulo,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, new String[]{"Cerrar"}, "Cerrar");
    }

    private static Restaurante pedirRestaurante() {
        JTextField txtNombre = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtCiudad = new JTextField();
        JTextField txtProvincia = new JTextField();
        JTextField txtCp = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Restaurante:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Ciudad:"));
        panel.add(txtCiudad);
        panel.add(new JLabel("Provincia:"));
        panel.add(txtProvincia);
        panel.add(new JLabel("Código postal:"));
        panel.add(txtCp);

        while (true) {
            int opcion = JOptionPane.showConfirmDialog(null, panel, "Añadir restaurante",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (opcion != JOptionPane.OK_OPTION) {
                return null;
            }

            String nombre = txtNombre.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String ciudad = txtCiudad.getText().trim();
            String provincia = txtProvincia.getText().trim();
            String cp = txtCp.getText().trim();

            String error = null;
            if (nombre.isEmpty() || direccion.isEmpty() || ciudad.isEmpty()
                    || provincia.isEmpty() || cp.isEmpty()) {
                error = "Rellena todos los campos.";
            }

            if (error != null) {
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            return new Restaurante(nombre, direccion, ciudad, provincia, cp);
        }
    }
}

