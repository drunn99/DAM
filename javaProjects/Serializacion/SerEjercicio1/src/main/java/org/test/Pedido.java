package org.test;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido implements Serializable {
    private static int contadorId = 1;

    private int idPedido;
    private int idCliente;
    private ArrayList<Producto> productosPedido;
    private LocalDate fechaPedido;
    private String estado;

    //region Constructors
    public Pedido(int idCliente, ArrayList<Producto> productosPedido, LocalDate fechaPedido, String estado) {
        this.idPedido = contadorId++;
        this.idCliente = idCliente;
        this.productosPedido = productosPedido;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
    }
    //endregion

    //region Contador
    public static void actualizarContador(int siguienteId) {
        contadorId = Math.max(contadorId, siguienteId);
    }
    //endregion

    //region Getters
    public int getIdPedido() {
        return idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public ArrayList<Producto> getProductosPedido() {
        return productosPedido;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public String getEstado() {
        return estado;
    }
    //endregion

    //region Setters
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setProductosPedido(ArrayList<Producto> productosPedido) {
        this.productosPedido = productosPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    //endregion

    //region Methods
    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", idCliente=" + idCliente +
                ", productosPedido=" + productosPedido +
                ", fechaPedido=" + fechaPedido +
                ", estado='" + estado + '\'' +
                '}';
    }
    //endregion
}
