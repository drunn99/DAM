package psp.ut2;

/**
 * Suma los elementos de un array repartiendo el trabajo entre varios hilos.
 * Cada hilo guarda su resultado en un atributo propio; main espera con join()
 * y combina los resultados parciales.
 */
public class SumaParalela {

    static class Sumador extends Thread {
        private final int[] datos;
        private final int desde, hasta;   // rango [desde, hasta)
        private long resultado;           // solo lo escribe este hilo

        Sumador(int[] datos, int desde, int hasta) {
            this.datos = datos;
            this.desde = desde;
            this.hasta = hasta;
        }

        @Override
        public void run() {
            long suma = 0;
            for (int i = desde; i < hasta; i++) {
                suma += datos[i];
            }
            resultado = suma;
        }

        long getResultado() {
            return resultado;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[] datos = new int[10_000_000];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = i % 100;
        }

        int numHilos = 4;
        Sumador[] hilos = new Sumador[numHilos];
        int tramo = datos.length / numHilos;

        // 1. Lanzar todos los hilos
        for (int i = 0; i < numHilos; i++) {
            int desde = i * tramo;
            int hasta = (i == numHilos - 1) ? datos.length : desde + tramo;
            hilos[i] = new Sumador(datos, desde, hasta);
            hilos[i].start();
        }

        // 2. Esperar a todos y 3. combinar los resultados
        long total = 0;
        for (Sumador h : hilos) {
            h.join();
            total += h.getResultado();
        }
        System.out.println("Suma total: " + total);
    }
}
