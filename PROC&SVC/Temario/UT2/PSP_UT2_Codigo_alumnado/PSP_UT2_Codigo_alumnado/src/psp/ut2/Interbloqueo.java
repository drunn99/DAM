package psp.ut2;

/**
 * Dos hilos transfieren dinero entre las mismas dos cuentas en sentidos opuestos.
 * Cada uno bloquea primero su cuenta de origen y luego la de destino: interbloqueo.
 */
public class Interbloqueo {

    static class Cuenta {
        final int id;
        int saldo;
        Cuenta(int id, int saldo) { this.id = id; this.saldo = saldo; }
    }

    // VERSIÓN CON INTERBLOQUEO
    static void transferir(Cuenta origen, Cuenta destino, int cantidad) {
        synchronized (origen) {
            pausa();                                  // facilita que el otro hilo entre
            synchronized (destino) {
                origen.saldo -= cantidad;
                destino.saldo += cantidad;
            }
        }
    }

    // VERSIÓN CORRECTA: todos los hilos bloquean las cuentas en el mismo orden (por id)
    static void transferirOrdenado(Cuenta origen, Cuenta destino, int cantidad) {
        Cuenta primera = origen.id < destino.id ? origen : destino;
        Cuenta segunda = origen.id < destino.id ? destino : origen;
        synchronized (primera) {
            pausa();
            synchronized (segunda) {
                origen.saldo -= cantidad;
                destino.saldo += cantidad;
            }
        }
    }

    static void pausa() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        boolean ordenado = args.length > 0 && args[0].equals("ordenado");
        Cuenta a = new Cuenta(1, 1000);
        Cuenta b = new Cuenta(2, 1000);

        Thread h1 = new Thread(() -> {
            if (ordenado) transferirOrdenado(a, b, 100); else transferir(a, b, 100);
        }, "Transferencia-A-B");
        Thread h2 = new Thread(() -> {
            if (ordenado) transferirOrdenado(b, a, 50); else transferir(b, a, 50);
        }, "Transferencia-B-A");

        h1.start();
        h2.start();
        h1.join(2000);
        h2.join(2000);

        if (h1.isAlive() || h2.isAlive()) {
            System.out.println("¡Interbloqueo! Estados: "
                    + h1.getState() + " / " + h2.getState());
            System.exit(1);
        }
        System.out.println("Saldos finales: A = " + a.saldo + ", B = " + b.saldo);
    }
}
