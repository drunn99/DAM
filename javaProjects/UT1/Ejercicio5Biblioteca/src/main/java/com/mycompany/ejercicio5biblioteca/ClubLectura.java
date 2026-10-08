/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio5biblioteca;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author drunn
 */
public class ClubLectura {

    private ArrayList<Prestamo> prestamos;
    private Set<Prestamo> prestamosSet = new HashSet<>();
    private File ficheroPrestamos;

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public ClubLectura(File ficheroPrestamos) {
        this.prestamos = new ArrayList<Prestamo>();
        this.ficheroPrestamos = ficheroPrestamos;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters">
    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }

    public Set<Prestamo> getPrestamosSet() {
        return prestamosSet;
    }

    public File getFicheroPrestamos() {
        return ficheroPrestamos;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setters">
    public void setPrestamos(ArrayList<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public void setFicheroPrestamos(File ficheroPrestamos) {
        this.ficheroPrestamos = ficheroPrestamos;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    public int setPrestamos() {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroPrestamos, StandardCharsets.UTF_8))) {
            String nextLine = "";

            while ((nextLine = br.readLine()) != null) {
                String[] splitData = nextLine.split(";");
                Object[] objectData = getPrestamoFromData(splitData);
                Prestamo prestamo = new Prestamo((Libro) objectData[1], (Miembro) objectData[0], (LocalDate) objectData[2]);
                this.prestamos.add(prestamo);
            }

            return 1;

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public Object[] getPrestamoFromData(String[] prestamoSplitData) {
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
        arrayParametros[2] = LocalDate.of(dateNumbers[0], dateNumbers[1], dateNumbers[2]);

        return arrayParametros;
    }

    public ArrayList<Prestamo> getPrestamoEditorial(String editorial) {
        ArrayList<Prestamo> filteredList = new ArrayList<Prestamo>();
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getLibroPrestado().getEditorial().equalsIgnoreCase(editorial)) {
                filteredList.add(prestamo);
            }
        }

        return filteredList;
    }

    public int nuevoPrestamo() throws IOException {
        Prestamo newPrestamo = pedirPrestamo();
        if (newPrestamo != null) {
	        if (prestamosSet.add(newPrestamo)) {
	        	//Añadirlo a prestamos
	            prestamos.add(newPrestamo);
	            
	            //Añadirlo al archivo de prestamos
	           try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroPrestamos, true))) {
	        	   bw.append("\n" + newPrestamo.toString());
	           }
	            
	            return 1;
	        }
        }
        return 0;
    }
    
     public int addDemoPrestamo() {
        Prestamo newPrestamo = pedirPrestamo();
        
        if (prestamosSet.add(newPrestamo)) {
            prestamos.add(newPrestamo);
            return 1;
        }
        return 0;
    }

    public File devoluciones(String anio) throws IOException {
        ArrayList<Prestamo> devoluciones = new ArrayList<>();
        File devolucionesFile = new File(".\\files\\devolucion.txt");
        int year = Integer.parseInt(anio);
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getFechaPrestamo().isBefore(LocalDate.ofYearDay(year, 1))) {
                devoluciones.add(prestamo);
            }
        }
        
        //Escribir en fichero devolcuiones
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(".\\files\\devolucion.txt"))) {
            for (Prestamo devolucion : devoluciones) {
                bw.append(devolucion.toString() + "\n");
            }

        } catch (IOException ex) {

        }

        return devolucionesFile;
    }

    private Prestamo pedirPrestamo() {
        JTextField txtNombre = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCiudad = new JTextField();
        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtEditorial = new JTextField();
        JTextField txtFecha = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Ciudad:"));
        panel.add(txtCiudad);
        panel.add(new JLabel("Título del libro:s"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Autor:"));
        panel.add(txtAutor);
        panel.add(new JLabel("Editorial:"));
        panel.add(txtEditorial);
        panel.add(new JLabel("Fecha préstamo (aaaa/mm/dd):"));
        panel.add(txtFecha);

        while (true) {
            int opcion = JOptionPane.showConfirmDialog(null, panel, "Añadir préstamo",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (opcion != JOptionPane.OK_OPTION) {
                return null;
            }

            try {
            	final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            	LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), FORMATO_FECHA);
            	
                int maxId = 0;
                for (Prestamo p : prestamos) {
                    if (p.getMiembro().getId() > maxId) {
                        maxId = p.getMiembro().getId();
                    }
                }
                int id = maxId + 1;

                return new Prestamo(
                        new Libro(
                        txtTitulo.getText().trim(),
                        txtAutor.getText().trim(),
                        txtEditorial.getText().trim()),
                        new Miembro(id,
                        txtNombre.getText().trim(),
                        txtEmail.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtCiudad.getText().trim()),
                        fecha);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error en la creación del préstamo", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public int cargarDemo() throws IOException {
        prestamos.clear();
        prestamosSet.clear();
        final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        ArrayList<Prestamo> demo = new ArrayList<Prestamo>();
        
        demo.add(new Prestamo(new Libro("El nombre del viento", "Patrick Rothfuss", "Anagrama"),
                new Miembro(101, "Carlos López Díaz", "carlos.lopez@gmail.com", "555123456", "Madrid"),
                LocalDate.of(2019, 9, 28)));
        
        demo.add(new Prestamo(new Libro("Cien años de soledad", "Gabriel García Márquez", "Sudamericana"),
                new Miembro(102, "Ana Martín Pérez", "ana.martin@newsvine.com", "555654321", "Barcelona"),
                LocalDate.of(2018, 3, 6)));
        
        demo.add(new Prestamo(new Libro("1984", "George Orwell", "Secker & Warburg"),
                new Miembro(103, "Luis Gómez Fernández", "luis.gomez@gmail.com", "555987654", "Valencia"),
                LocalDate.of(2017, 12, 6)));
        
        demo.add(new Prestamo(new Libro("Orgullo y prejuicio", "Jane Austen", "T. Egerton"),
                new Miembro(104, "Marta Sánchez Ruiz", "marta.sanchez@webs.com", "555456789", "Sevilla"),
                LocalDate.of(2020, 11, 29)));
        
        demo.add(new Prestamo(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "Francisco de Robles"),
                new Miembro(105, "Javier Torres López", "javier.torres@gmail.com", "555321987", "Zaragoza"),
                LocalDate.of(2012, 3, 29)));

        for (Prestamo p : demo) {
            if (prestamosSet.add(p)) {
                prestamos.add(p);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroPrestamos))) {
            for (int i = 0; i < prestamos.size(); i++) {
                if (i > 0) bw.newLine();
                bw.write(prestamos.get(i).toString());
            }
        }
        return prestamos.size();
    }
    // </editor-fold>
}
