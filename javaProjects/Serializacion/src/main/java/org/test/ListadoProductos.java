package org.test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListadoProductos {
    private ArrayList<Producto> productos;

    public ListadoProductos() {
        this.productos = new ArrayList<Producto>();
    }

    //region Methods
    public Producto buscarProductoDisponiblePorNombre(String nombreProducto) {
        for (Producto producto : productos) {
            if (producto.getNombreProducto() == nombreProducto && producto.getCantidadDisponible() > 0) {
                return producto;
            }
        }
        return null;
    }

    public int addProducto(Producto newProducto) {
        if (buscarProductoDisponiblePorNombre(newProducto.getNombreProducto()) == null) {
            productos.add(newProducto);
            return 0;
        } else {
            actualizarCantidad(newProducto.getCantidadDisponible(),newProducto.getIdProdcucto());
        }
        return 1;
    }

    public void actualizarCantidad(int cantidad, int idProducto) {
        for (Producto producto : productos) {
            if (producto.getIdProdcucto() == idProducto) {
                producto.setCantidadDisponible(producto.getCantidadDisponible() + cantidad);            }
        }
    }

    public ArrayList<Producto> ordenarProductosPorPrecio() {
        ArrayList<Producto> sortedProductos = new ArrayList<Producto>();
        Collections.copy(sortedProductos,productos);
        sortedProductos.sort(Comparator.comparing(Producto::getCantidadDisponible));

        return sortedProductos;
    }

    public int actualizarFicheroProductos() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("..\\files\\productos.ser"));) {
            out.writeObject(productos);
            return 0;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 1;
        }
    }

    public int cargarDatosProductos() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("..\\files\\productos.ser"));) {
            productos = (ArrayList<Producto>) in.readObject();
            return 0;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return 1;
        }
    }
    //endregion
}
