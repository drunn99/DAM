package psp.ut2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Varios hilos cuentan palabras de distintas líneas y acumulan el resultado
 * en un único ConcurrentHashMap compartido. merge() es una operación atómica.
 */
public class FrecuenciaPalabras {

    public static void main(String[] args) {
        List<String> lineas = List.of(
                "el hilo principal crea un hilo nuevo",
                "un hilo comparte la memoria con otro hilo",
                "la memoria compartida exige sincronizar",
                "sincronizar bien evita la condición de carrera");

        Map<String, Integer> frecuencias = new ConcurrentHashMap<>();

        try (ExecutorService pool = Executors.newFixedThreadPool(4)) {
            for (String linea : lineas) {
                pool.submit(() -> {
                    for (String palabra : linea.split("\\s+")) {
                        frecuencias.merge(palabra, 1, Integer::sum);   // atómico
                    }
                });
            }
        }
        System.out.println("hilo -> " + frecuencias.get("hilo"));
        System.out.println("la -> " + frecuencias.get("la"));
        System.out.println("Palabras distintas: " + frecuencias.size());
    }
}
