package psp.ut2;

/**
 * Cuatro formas de crear e iniciar un hilo en Java.
 * Ejecuta el programa varias veces: el orden de las líneas cambia.
 */
public class CrearHilos {

    // Forma 1: heredar de Thread y sobrescribir run()
    static class Saludador extends Thread {
        public Saludador(String nombre) {
            super(nombre);                     // nombre del hilo
        }
        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                System.out.println(getName() + " -> vuelta " + i);
            }
        }
    }

    // Forma 2: implementar Runnable (la tarea va separada del hilo)
    static class Tarea implements Runnable {
        @Override
        public void run() {
            String nombre = Thread.currentThread().getName();
            for (int i = 1; i <= 3; i++) {
                System.out.println(nombre + " -> vuelta " + i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread h1 = new Saludador("Hilo-A");
        Thread h2 = new Thread(new Tarea(), "Hilo-B");

        // Forma 3: Runnable escrito como expresión lambda
        Thread h3 = new Thread(
                () -> System.out.println("Hola desde " + Thread.currentThread().getName()),
                "Hilo-C");

        // Forma 4: Thread.Builder (Java 21): crea y arranca en una sola instrucción
        Thread h4 = Thread.ofPlatform().name("Hilo-D").start(new Tarea());

        h1.start();
        h2.start();
        h3.start();

        System.out.println("main ha lanzado todos los hilos");

        h1.join();
        h2.join();
        h3.join();
        h4.join();
        System.out.println("main: todos los hilos han terminado");
    }
}
