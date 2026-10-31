package psp.ut2;

/** Diferencia entre llamar a run() y a start(). */
public class StartVsRun {

    public static void main(String[] args) throws InterruptedException {
        Runnable tarea = () ->
                System.out.println("Ejecutando en: " + Thread.currentThread().getName());

        Thread t = new Thread(tarea, "Trabajador");

        t.run();     // ERROR habitual: llamada normal a un método; NO crea un hilo
        t.start();   // Correcto: crea un hilo nuevo que ejecutará run()
        t.join();
    }
}
