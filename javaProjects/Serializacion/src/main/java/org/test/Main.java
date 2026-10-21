package org.test;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        ListadoClientes clientes = new ListadoClientes();
        ListadoProductos productos = new ListadoProductos();
        ListadoPedidos pedidos = new ListadoPedidos();

        int response = -1;

        String[] options = {
                "Gestión Clientes",
                "Gestión Productos",
                "Gestión Pedidos",
                "Cargar Demo",
                "Salir"
        };

        while (response != 4) {
            response = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Ejercicio Serialización - PedidosOnline",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            switch (response){
                case 0: //Gestión Clientes

                    break;
                case 1: //Gestion Productos

                    break;
                case 2: //Gestión Pedidos

                    break;
                case 3: //Cargar Demo

                    break;
            }

        }
    }


}
