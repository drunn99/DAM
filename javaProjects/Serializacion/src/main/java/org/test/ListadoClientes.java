package org.test;

import java.io.*;
import java.util.ArrayList;

public class ListadoClientes {
    private ArrayList<Cliente> clientes;

    //region Constructors
    public ListadoClientes() {
        this.clientes = new ArrayList<Cliente>();
    }
    //endregion

    //region Methods
    public Object buscarClientePorId(int idCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == idCliente) {
                return cliente;
            }
        }
        return null;
    }

    public int addCliente(Cliente newCliente) {
        if (buscarClientePorId(newCliente.getIdCliente()) == null) {
            clientes.add(newCliente);
            return 0;
        }
        return 1;
    }

    public int actualizarFicheroClientes() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("..\\files\\clientes.ser"));) {
            out.writeObject(clientes);
            return 0;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 1;
        }
    }

    public int cargarDatosClientes() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("..\\files\\clientes.ser"));) {
            clientes = (ArrayList<Cliente>) in.readObject();
            return 0;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return 1;
        }
    }
    //endregion
}
