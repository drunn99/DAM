package org.test;

import java.io.*;
import java.util.ArrayList;

public class ListadoPedidos {
    private ArrayList<Pedido> pedidos;

    public ListadoPedidos() {
        this.pedidos = new ArrayList<Pedido>();
    }

    //region Methods
    public int addPedido(Pedido newPedido) {
        try {
            pedidos.add(newPedido);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public ArrayList<Pedido> pedidosPorCliente(int idCliente) {
        ArrayList<Pedido> pedidosFiltered = new ArrayList<Pedido>();
        for (Pedido pedido : pedidos) {
            if (pedido.getIdCliente() == idCliente) {
                pedidosFiltered.add(pedido);
            }
        }

        return pedidosFiltered;
    }

    public int actualizarEstadoPedido(int idPedido, String nuevoEstado) {
        for (Pedido pedido : pedidos) {
            if (pedido.getIdPedido() == idPedido) {
                pedido.setEstado(nuevoEstado);
                return 0;
            }
        }
        return 1;
    }

    public int actualizarFicheroPedidos() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(".\\files\\pedidos.ser"));) {
            out.writeObject(pedidos);
            return 0;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 1;
        }
    }

    public int cargarDatosPedidos() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(".\\files\\pedidos.ser"));) {
            pedidos = (ArrayList<Pedido>) in.readObject();
            sincronizarContador();
            return 0;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return 1;
        }
    }

    private void sincronizarContador() {
        int maxId = 0;
        for (Pedido pedido : pedidos) {
            maxId = Math.max(maxId, pedido.getIdPedido());
        }
        Pedido.actualizarContador(maxId + 1);
    }
    //endregion
}
