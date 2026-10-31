package psp.ut2;

/**
 * Detener un hilo de forma cooperativa con interrupt().
 * El hilo comprueba si le han pedido parar y, si está dormido, sale con InterruptedException.
 */
public class Interrupciones {

    public static void main(String[] args) throws InterruptedException {
        Thread vigilante = new Thread(() -> {
            int ronda = 0;
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    ronda++;
                    System.out.println("Ronda de vigilancia " + ronda);
                    Thread.sleep(400);           // si lo interrumpen aquí, salta la excepción
                }
            } catch (InterruptedException e) {
                // sleep() limpia el indicador de interrupción al lanzar la excepción
                System.out.println("Me han interrumpido mientras dormía");
            }
            System.out.println("Vigilante: libero recursos y termino");
        }, "Vigilante");

        vigilante.start();
        Thread.sleep(1500);
        System.out.println("main: pido al vigilante que pare");
        vigilante.interrupt();
        vigilante.join();
        System.out.println("main: el vigilante ha terminado");
    }
}
