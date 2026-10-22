package org.test;

import java.io.Serializable;

public class Producto implements Serializable {
    private static int contadorId = 1;

    private int idProdcucto;
    private String nombreProducto;
    private String descripcion;
    private double precio;
    private int cantidadDisponible;

    //region Constructors
    public Producto(String nombreProducto, String descripcion, double precio, int cantidadDisponible) {
        this.idProdcucto = contadorId++;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
    }
    //endregion

    //region Contador
    public static void actualizarContador(int siguienteId) {
        contadorId = Math.max(contadorId, siguienteId);
    }
    //endregion

    //region Getters
    public int getIdProdcucto() {
        return idProdcucto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }
    //endregion

    //region Setters
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    //endregion

    //region Methods
    @Override
    public String toString() {
        return "Producto{" +
                "idProdcucto=" + idProdcucto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", cantidadDisponible=" + cantidadDisponible +
                '}';
    }
    //endregion
}
