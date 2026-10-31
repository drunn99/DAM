package psp.ut2;

/**
 * Problema de visibilidad: el hilo puede no ver nunca el cambio de la bandera.
 * Prueba a quitar volatile: en muchos equipos el programa no termina.
 */
public class Visibilidad {

    private static volatile boolean ejecutando = true;   // quita volatile para ver el problema

    public static void main(String[] args) throws InterruptedException {
        Thread trabajador = new Thread(() -> {
            long vueltas = 0;
            while (ejecutando) {
                vueltas++;
            }
            System.out.println("Trabajador detenido tras " + vueltas + " vueltas");
        });
        trabajador.start();

        Thread.sleep(1000);
        ejecutando = false;          // main cambia la bandera
        System.out.println("main: bandera puesta a false");
        trabajador.join();
    }
}
