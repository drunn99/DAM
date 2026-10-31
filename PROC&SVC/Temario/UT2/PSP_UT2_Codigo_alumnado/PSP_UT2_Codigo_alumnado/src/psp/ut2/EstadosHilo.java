package psp.ut2;

/** Muestra los estados por los que pasa un hilo, consultándolos con getState(). */
public class EstadosHilo {

    private static final Object cerrojo = new Object();

    public static void main(String[] args) throws InterruptedException {
        // 1. NEW, RUNNABLE, TIMED_WAITING y TERMINATED
        Thread dormilon = new Thread(() -> {
            long fin = System.currentTimeMillis() + 300;
            while (System.currentTimeMillis() < fin) { }        // trabaja (RUNNABLE)
            try {
                Thread.sleep(500);                               // duerme (TIMED_WAITING)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Dormilon");

        System.out.println("Antes de start():     " + dormilon.getState());
        dormilon.start();
        Thread.sleep(100);
        System.out.println("Calculando:           " + dormilon.getState());
        Thread.sleep(400);
        System.out.println("Dentro de sleep():    " + dormilon.getState());
        dormilon.join();
        System.out.println("Tras terminar:        " + dormilon.getState());

        // 2. BLOCKED: el hilo quiere entrar en un bloque synchronized ocupado por main
        Thread bloqueado = new Thread(() -> {
            synchronized (cerrojo) {
                System.out.println("Bloqueado ha conseguido el cerrojo");
            }
        }, "Bloqueado");
        synchronized (cerrojo) {
            bloqueado.start();
            Thread.sleep(200);
            System.out.println("Esperando el cerrojo: " + bloqueado.getState());
        }
        bloqueado.join();

        // 3. WAITING: el hilo espera en wait() hasta que alguien lo notifique
        Thread esperando = new Thread(() -> {
            synchronized (cerrojo) {
                try {
                    cerrojo.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Esperando");
        esperando.start();
        Thread.sleep(200);
        System.out.println("Dentro de wait():     " + esperando.getState());
        synchronized (cerrojo) {
            cerrojo.notify();
        }
        esperando.join();
    }
}
