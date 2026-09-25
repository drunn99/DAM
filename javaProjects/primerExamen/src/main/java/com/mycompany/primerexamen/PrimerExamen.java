/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.primerexamen;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author drunn
 */
public class PrimerExamen {

    public static void main(String[] args) {
        //EJECUTAR EJERCICIOS
        primerEjercicio();
        segundoEjercicio();
        tercerEjercicio();
        cuartoEjercicio();
        quintoEjercicio();
    }

    public static int[] generarArrayRandom(int length, int start, int end) {
        Random random = new Random();
        int[] randomNumbers = random.ints(length, start, end).toArray();
        return randomNumbers;
    }

    public static void primerEjercicio() {
        int[] randomNumbers = generarArrayRandom(18, 0, 100);

        int sumGreater = 0;
        int sumLesser = 0;
        int equal = 0;

        for (int i = 0; i < randomNumbers.length; i++) {
            if (i > 0) {
                if (randomNumbers[i] > randomNumbers[i - 1]) {
                    sumGreater += 1;
                }
                if (randomNumbers[i] < randomNumbers[i - 1]) {
                    sumLesser += 1;
                }
                if (randomNumbers[i] == randomNumbers[i - 1]) {
                    equal += 1;
                }
            }
        }

        System.out.println("--- PRIMER EJERCICIO ---");
        System.out.println(Arrays.toString(randomNumbers));
        System.out.println("Mayor que el anterior: " + sumGreater);
        System.out.println("Menor que el anterior: " + sumLesser);
        System.out.println("Igual que el anterior: " + equal);
    }

    public static void segundoEjercicio() {
        int[] randomNumbers = generarArrayRandom(25, -20, 20);

        System.out.println("--- SEGUNDO EJERCICIO ---");
        System.out.println(Arrays.toString(randomNumbers));

        for (int i = 0; i < randomNumbers.length; i++) {
            for (int j = 0; j < randomNumbers.length; j++) {
                if (randomNumbers[i] > randomNumbers[j]) {
                    int auxN = randomNumbers[j];
                    randomNumbers[j] = randomNumbers[i];
                    randomNumbers[i] = auxN;
                }
            }
            if (randomNumbers[i] < 0) {
                randomNumbers[i] = 0;
            }
        }

        System.out.println(Arrays.toString(randomNumbers));

    }

    public static void tercerEjercicio() {
        int[] randomNumbers = generarArrayRandom(10, 1, 50);

        System.out.println("--- TERCER EJERCICIO ---");
        System.out.println("Original: " + Arrays.toString(randomNumbers));

        Scanner sc = new Scanner(System.in);

        System.out.print("Introduzca el valor de rotación: ");
        int rawShift = sc.nextInt();

        int n = randomNumbers.length;
        int shiftNumber = rawShift % n;

        int[] rotated = new int[n];

        for (int i = 0; i < n; i++) {
            rotated[(i + shiftNumber) % n] = randomNumbers[i];
        }

        System.out.println("Rotado:   " + Arrays.toString(rotated));

    }

    public static void cuartoEjercicio() {
        int[][] matrix = new int[6][4];
        Random randomNumber = new Random();

        int biggest = 0;
        int smallest = 0;
        int diff = 0;

        System.out.println("--- CUARTO EJERCICIO ---");

        for (int i = 0; i < matrix.length; i++) {
            System.out.print("[");
            for (int j = 0; j < matrix[i].length; j++) {
                //INTRODUCE DATOS
                matrix[i][j] = randomNumber.nextInt(1, 30);
                //IMPRIMIR DATOS
                System.out.print(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    System.out.print(",");
                }
                //CALCULAR MAYOR, MENOR Y DIFERENCIA ENTRE AMBOS
                if (j > 0) {
                    if (matrix[i][j] > matrix[i][j - 1]) {
                        biggest = matrix[i][j];
                    }

                    if (matrix[i][j] < matrix[i][j - 1]) {
                        smallest = matrix[i][j];
                    }
                }
            }
            diff = biggest - smallest;
            System.out.println("] - Mayor: " + biggest + ", Menor: " + smallest + ", Diferencia: " + diff);
        }

    }

    public static void quintoEjercicio() {
        int[][] matrix = new int[5][5];
        Random randomNumber = new Random();

        System.out.println("--- QUINTO EJERCICIO ---");

        int[] diagonal = new int[5];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                //INTRODUCE DATOS EN MATRIZ Y DIAGONAL
                matrix[i][j] = randomNumber.nextInt(1, 20);
                if (i == j) {
                    diagonal[i] = matrix[i][j];
                }
            }
        }

        //MOSTRAR MATRIZ
        for (int[] is : matrix) {
            System.out.print("[");
            for (int i : is) {
                System.out.print(i + ",");
            }
            System.out.println("]");
        }

        //CALCULAR MOVIDAS
        int sum = 0;
        float avg = 0;
        int countGreater = 0;

        for (int i = 0; i < diagonal.length; i++) {
            sum += diagonal[i];
        }

        avg = sum / diagonal.length;

        for (int i = 0; i < diagonal.length; i++) {
            if (diagonal[i] > avg) {
                countGreater += 1;
            }

        }
        System.out.println("Diagonal: \n" + Arrays.toString(diagonal));
        System.out.println("La suma de la diagonal es: " + sum);
        System.out.println("La media de la diagonal es: " + avg);
        System.out.println("Hay: " + countGreater + " valores mayores de: " + avg + " en la diagonal");
    }
}
