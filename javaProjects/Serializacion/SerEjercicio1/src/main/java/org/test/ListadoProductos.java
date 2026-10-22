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
            if (producto.getNombreProducto().equalsIgnoreCase(nombreProducto) && producto.getCantidadDisponible() > 0) {
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
        sortedProductos.addAll(productos);
        sortedProductos.sort(Comparator.comparing(Producto::getPrecio));

        return sortedProductos;
    }

    public int actualizarFicheroProductos() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(".\\files\\productos.ser"));) {
            out.writeObject(productos);
            return 0;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 1;
        }
    }

    public int cargarDatosProductos() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(".\\files\\productos.ser"));) {
            productos = (ArrayList<Producto>) in.readObject();
            sincronizarContador();
            return 0;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return 1;
        }
    }

    private void sincronizarContador() {
        int maxId = 0;
        for (Producto producto : productos) {
            maxId = Math.max(maxId, producto.getIdProdcucto());
        }
        Producto.actualizarContador(maxId + 1);
    }
    //endregion
}
