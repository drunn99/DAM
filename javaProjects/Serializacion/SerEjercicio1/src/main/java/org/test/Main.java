package org.test;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    /*
    *   Hola Marian, como en el ejercicio 5 de la biblioteca te he dejado un botón "Cargar Demo" que introduce datos a pelo en los .ser y los carga directamente
    *   en los objetos pertinentes para que puedas tocar los menús ya con datos, he trasteado un poco con swing para probar a hacer menús desplegables y tal.
    * */
    private static final String[] ESTADOS = {"Pendiente", "Enviado", "Entregado"};
    static void main() {
        ListadoClientes clientes = new ListadoClientes();
        ListadoProductos productos = new ListadoProductos();
        ListadoPedidos pedidos = new ListadoPedidos();

        new File(".\\files").mkdirs();
        if (new File(".\\files\\clientes.ser").exists()) {
            clientes.cargarDatosClientes();
            productos.cargarDatosProductos();
            pedidos.cargarDatosPedidos();
        }

        String[] opciones = {"Gestión Clientes", "Gestión Productos", "Gestión Pedidos", "Cargar Demo", "Salir"};

        boolean salir = false;
        while (!salir) {
            switch (mostrarMenu("PedidosOnline", opciones)) {
                case 0 -> menuClientes(clientes);
                case 1 -> menuProductos(productos);
                case 2 -> menuPedidos(pedidos, clientes, productos);
                case 3 -> cargarDemo(clientes, productos, pedidos);
                default -> salir = true; // "Salir" o cerrar la ventana
            }
        }
    }

    //region Utilidades
    private static int mostrarMenu(String titulo, String[] opciones) {
        return JOptionPane.showOptionDialog(null, "Seleccione una opción", titulo,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
    }

    private static void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    private static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    private static int pedirEntero(String mensaje) {
        while (true) {
            String texto = JOptionPane.showInputDialog(mensaje);
            if (texto == null) {
                return -1;
            }
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                mostrarError("Introduce un número entero.");
            }
        }
    }
    //endregion

    //region Clientes
    private static void menuClientes(ListadoClientes clientes) {
        String[] opciones = {"Ver cliente por ID", "Añadir cliente", "Volver"};

        boolean volver = false;
        while (!volver) {
            switch (mostrarMenu("Gestión Clientes", opciones)) {
                case 0 -> {
                    int id = pedirEntero("ID del cliente:");
                    if (id != -1) {
                        Object cliente = clientes.buscarClientePorId(id);
                        mostrar(cliente == null ? "No existe ese cliente." : cliente.toString());
                    }
                }
                case 1 -> {
                    Cliente nuevo = pedirCliente();
                    if (nuevo != null) {
                        clientes.addCliente(nuevo);
                        clientes.actualizarFicheroClientes();
                        mostrar("Cliente añadido con ID " + nuevo.getIdCliente());
                    }
                }
                default -> volver = true;
            }
        }
    }

    private static Cliente pedirCliente() {
        JTextField txtNombre = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtTelefono = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);

        while (true) {
            int opcion = JOptionPane.showConfirmDialog(null, panel, "Añadir cliente",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (opcion != JOptionPane.OK_OPTION) {
                return null;
            }

            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
                mostrarError("Rellena todos los campos.");
                continue;
            }

            return new Cliente(nombre, email, direccion, telefono);
        }
    }
    //endregion

    //region Productos
    private static void menuProductos(ListadoProductos productos) {
        String[] opciones = {"Ver producto por nombre", "Listado por precio", "Añadir producto", "Volver"};

        boolean volver = false;
        while (!volver) {
            switch (mostrarMenu("Gestión Productos", opciones)) {
                case 0 -> {
                    String nombre = JOptionPane.showInputDialog("Nombre del producto:");
                    if (nombre != null) {
                        Producto producto = productos.buscarProductoDisponiblePorNombre(nombre.trim());
                        mostrar(producto == null ? "No hay ningún producto disponible con ese nombre." : producto.toString());
                    }
                }
                case 1 -> {
                    StringBuilder listado = new StringBuilder();
                    for (Producto producto : productos.ordenarProductosPorPrecio()) {
                        listado.append(producto.getNombreProducto())
                                .append(" - ").append(producto.getPrecio()).append(" €\n");
                    }
                    mostrar(listado.isEmpty() ? "No hay productos." : listado.toString());
                }
                case 2 -> {
                    Producto nuevo = pedirProducto();
                    if (nuevo != null) {
                        productos.addProducto(nuevo);
                        productos.actualizarFicheroProductos();
                        mostrar("Producto guardado.");
                    }
                }
                default -> volver = true;
            }
        }
    }

    private static Producto pedirProducto() {
        JTextField txtNombre = new JTextField();
        JTextField txtDescripcion = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtCantidad = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);

        while (true) {
            int opcion = JOptionPane.showConfirmDialog(null, panel, "Añadir producto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (opcion != JOptionPane.OK_OPTION) {
                return null;
            }

            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                mostrarError("Rellena todos los campos.");
                continue;
            }

            try {
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                return new Producto(nombre, descripcion, precio, cantidad);
            } catch (NumberFormatException e) {
                mostrarError("El precio y la cantidad deben ser números.");
            }
        }
    }
    //endregion

    //region Pedidos
    private static void menuPedidos(ListadoPedidos pedidos, ListadoClientes clientes, ListadoProductos productos) {
        String[] opciones = {"Realizar pedido", "Pedidos por cliente", "Actualizar estado", "Volver"};

        boolean volver = false;
        while (!volver) {
            switch (mostrarMenu("Gestión Pedidos", opciones)) {
                case 0 -> {
                    Pedido nuevo = pedirPedido(clientes, productos);
                    if (nuevo != null) {
                        pedidos.addPedido(nuevo);
                        pedidos.actualizarFicheroPedidos();
                        mostrar("Pedido creado con ID " + nuevo.getIdPedido());
                    }
                }
                case 1 -> {
                    int idCliente = pedirEntero("ID del cliente:");
                    if (idCliente != -1) {
                        StringBuilder listado = new StringBuilder();
                        for (Pedido pedido : pedidos.pedidosPorCliente(idCliente)) {
                            listado.append(pedido).append("\n");
                        }
                        mostrar(listado.isEmpty() ? "Ese cliente no tiene pedidos." : listado.toString());
                    }
                }
                case 2 -> {
                    int idPedido = pedirEntero("ID del pedido:");
                    if (idPedido != -1) {
                        String estado = (String) JOptionPane.showInputDialog(null, "Nuevo estado:", "Actualizar estado",
                                JOptionPane.PLAIN_MESSAGE, null, ESTADOS, ESTADOS[0]);
                        if (estado != null) {
                            if (pedidos.actualizarEstadoPedido(idPedido, estado) == 0) {
                                pedidos.actualizarFicheroPedidos();
                                mostrar("Estado actualizado.");
                            } else {
                                mostrarError("No existe ese pedido.");
                            }
                        }
                    }
                }
                default -> volver = true;
            }
        }
    }

    private static Pedido pedirPedido(ListadoClientes clientes, ListadoProductos productos) {
        JTextField txtIdCliente = new JTextField();
        JTextField txtProductos = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("ID del cliente:"));
        panel.add(txtIdCliente);
        panel.add(new JLabel("Productos (separados por comas):"));
        panel.add(txtProductos);

        while (true) {
            int opcion = JOptionPane.showConfirmDialog(null, panel, "Realizar pedido",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (opcion != JOptionPane.OK_OPTION) {
                return null;
            }

            int idCliente;
            try {
                idCliente = Integer.parseInt(txtIdCliente.getText().trim());
            } catch (NumberFormatException e) {
                mostrarError("El ID del cliente debe ser un número.");
                continue;
            }

            if (clientes.buscarClientePorId(idCliente) == null) {
                mostrarError("No existe ese cliente.");
                continue;
            }

            ArrayList<Producto> productosPedido = new ArrayList<>();
            String noEncontrado = null;
            for (String nombre : txtProductos.getText().split(",")) {
                Producto producto = productos.buscarProductoDisponiblePorNombre(nombre.trim());
                if (producto == null) {
                    noEncontrado = nombre.trim();
                    break;
                }
                productosPedido.add(producto);
            }

            if (noEncontrado != null) {
                mostrarError("No hay ningún producto disponible llamado \"" + noEncontrado + "\".");
                continue;
            }

            return new Pedido(idCliente, productosPedido, LocalDate.now(), ESTADOS[0]);
        }
    }
    //endregion
    //region Demo
    private static void cargarDemo(ListadoClientes clientes, ListadoProductos productos, ListadoPedidos pedidos) {
        new File(".\\files").mkdirs();

        //region Clientes demo
        Cliente[] demoClientes = {
                new Cliente("Lucía Martín", "lucia.martin@mail.com", "C/ Vitoria 12, Burgos", "600111222"),
                new Cliente("Javier Gómez", "javi.gomez@mail.com", "Av. del Cid 45, Burgos", "600222333"),
                new Cliente("Marta Ruiz", "marta.ruiz@mail.com", "C/ Mayor 3, Valladolid", "600333444"),
                new Cliente("Sergio Alonso", "sergio.alonso@mail.com", "Pl. España 8, León", "600444555"),
                new Cliente("Elena Sanz", "elena.sanz@mail.com", "C/ Laín Calvo 21, Burgos", "600555666"),
                new Cliente("Pablo Herrero", "pablo.herrero@mail.com", "C/ Santiago 15, Valladolid", "600666777"),
                new Cliente("Ana Delgado", "ana.delgado@mail.com", "Av. Reyes Católicos 30, Burgos", "600777888"),
                new Cliente("Diego Castro", "diego.castro@mail.com", "C/ Ancha 5, León", "600888999"),
                new Cliente("Laura Pérez", "laura.perez@mail.com", "C/ Real 18, Segovia", "600999000"),
                new Cliente("Álvaro Moreno", "alvaro.moreno@mail.com", "C/ Zamora 9, Salamanca", "611000111"),
                new Cliente("Carmen Ortega", "carmen.ortega@mail.com", "C/ San Juan 2, Burgos", "611111222"),
                new Cliente("Raúl Vega", "raul.vega@mail.com", "Paseo Zorrilla 77, Valladolid", "611222333")
        };
        for (Cliente c : demoClientes) {
            clientes.addCliente(c);
        }
        //endregion

        //region Productos demo
        Producto[] demoProductos = {
                new Producto("Teclado mecánico", "Teclado switches rojos, layout ES", 79.99, 25),
                new Producto("Ratón inalámbrico", "Ratón ergonómico 2.4 GHz", 24.50, 60),
                new Producto("Monitor 27\"", "Monitor IPS 1440p 144 Hz", 289.00, 12),
                new Producto("Auriculares", "Auriculares con micrófono y cancelación de ruido", 59.90, 40),
                new Producto("Webcam HD", "Webcam 1080p con micrófono integrado", 39.95, 30),
                new Producto("SSD 1TB", "Unidad NVMe PCIe 4.0", 89.00, 50),
                new Producto("Memoria USB 64GB", "Pendrive USB 3.2", 9.99, 150),
                new Producto("Alfombrilla XL", "Alfombrilla de tela 90x40 cm", 14.95, 80),
                new Producto("Hub USB-C", "Hub 7 en 1 con HDMI y lector SD", 34.99, 35),
                new Producto("Silla gaming", "Silla ergonómica reclinable", 199.00, 8),
                new Producto("Lámpara LED", "Lámpara de escritorio regulable", 27.50, 45),
                new Producto("Portátil 15\"", "Portátil i5, 16 GB RAM, 512 GB SSD", 749.00, 6)
        };
        for (Producto p : demoProductos) {
            productos.addProducto(p);
        }
        //endregion

        //region Pedidos demo
        Pedido[] demoPedidos = {
                new Pedido(demoClientes[0].getIdCliente(), new ArrayList<>(List.of(demoProductos[0], demoProductos[1])),
                        LocalDate.of(2026, 9, 2), "Entregado"),
                new Pedido(demoClientes[1].getIdCliente(), new ArrayList<>(List.of(demoProductos[2])),
                        LocalDate.of(2026, 9, 5), "Entregado"),
                new Pedido(demoClientes[2].getIdCliente(), new ArrayList<>(List.of(demoProductos[5], demoProductos[6], demoProductos[8])),
                        LocalDate.of(2026, 9, 11), "Enviado"),
                new Pedido(demoClientes[0].getIdCliente(), new ArrayList<>(List.of(demoProductos[3])),
                        LocalDate.of(2026, 9, 18), "Enviado"),
                new Pedido(demoClientes[3].getIdCliente(), new ArrayList<>(List.of(demoProductos[9], demoProductos[10])),
                        LocalDate.of(2026, 9, 25), "Cancelado"),
                new Pedido(demoClientes[4].getIdCliente(), new ArrayList<>(List.of(demoProductos[11])),
                        LocalDate.of(2026, 10, 1), "Pendiente"),
                new Pedido(demoClientes[5].getIdCliente(), new ArrayList<>(List.of(demoProductos[4], demoProductos[7])),
                        LocalDate.of(2026, 10, 4), "Pendiente"),
                new Pedido(demoClientes[1].getIdCliente(), new ArrayList<>(List.of(demoProductos[6])),
                        LocalDate.of(2026, 10, 8), "Enviado"),
                new Pedido(demoClientes[6].getIdCliente(), new ArrayList<>(List.of(demoProductos[0], demoProductos[2], demoProductos[3])),
                        LocalDate.of(2026, 10, 12), "Pendiente"),
                new Pedido(demoClientes[7].getIdCliente(), new ArrayList<>(List.of(demoProductos[8])),
                        LocalDate.of(2026, 10, 15), "Entregado"),
                new Pedido(demoClientes[8].getIdCliente(), new ArrayList<>(List.of(demoProductos[1], demoProductos[7])),
                        LocalDate.of(2026, 10, 18), "Pendiente"),
                new Pedido(demoClientes[9].getIdCliente(), new ArrayList<>(List.of(demoProductos[5])),
                        LocalDate.of(2026, 10, 20), "Pendiente")
        };
        for (Pedido p : demoPedidos) {
            pedidos.addPedido(p);
        }
        //endregion

        int errores = clientes.actualizarFicheroClientes()
                + productos.actualizarFicheroProductos()
                + pedidos.actualizarFicheroPedidos();

        if (errores == 0) {
            JOptionPane.showMessageDialog(null,
                    "Demo cargada y guardada:\n"
                            + demoClientes.length + " clientes\n"
                            + demoProductos.length + " productos\n"
                            + demoPedidos.length + " pedidos",
                    "Cargar Demo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Falló el guardado de ficheros",
                    "Cargar Demo", JOptionPane.ERROR_MESSAGE);
        }
    }
    //endregion


}
