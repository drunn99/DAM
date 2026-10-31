package psp.ut2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/** Tres formas correctas de resolver la condición de carrera del contador. */
public class ContadoresSeguros {

    // Solución 1: método synchronized
    static class ContadorSync {
        private int valor = 0;
        synchronized void incrementar() { valor++; }
        synchronized int getValor() { return valor; }
    }

    // Solución 2: cerrojo explícito
    static class ContadorLock {
        private final ReentrantLock cerrojo = new ReentrantLock();
        private int valor = 0;
        void incrementar() {
            cerrojo.lock();
            try {
                valor++;
            } finally {
                cerrojo.unlock();          // siempre en finally
            }
        }
        int getValor() {
            cerrojo.lock();
            try { return valor; } finally { cerrojo.unlock(); }
        }
    }

    // Solución 3: variable atómica
    static class ContadorAtomico {
        private final AtomicInteger valor = new AtomicInteger();
        void incrementar() { valor.incrementAndGet(); }
        int getValor() { return valor.get(); }
    }

    static void lanzar(String nombre, Runnable incremento, java.util.function.IntSupplier lectura)
            throws InterruptedException {
        Thread[] hilos = new Thread[4];
        long inicio = System.nanoTime();
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < 1_000_000; j++) incremento.run();
            });
            hilos[i].start();
        }
        for (Thread h : hilos) h.join();
        long ms = (System.nanoTime() - inicio) / 1_000_000;
        System.out.printf("%-12s valor = %d  (%d ms)%n", nombre, lectura.getAsInt(), ms);
    }

    public static void main(String[] args) throws InterruptedException {
        ContadorSync c1 = new ContadorSync();
        ContadorLock c2 = new ContadorLock();
        ContadorAtomico c3 = new ContadorAtomico();
        lanzar("synchronized", c1::incrementar, c1::getValor);
        lanzar("Lock", c2::incrementar, c2::getValor);
        lanzar("Atomic", c3::incrementar, c3::getValor);
    }
}
