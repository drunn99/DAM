package psp.ut1;

/**
 * Proceso hijo: cuenta los números primos de un rango [desde, hasta].
 * Uso: java psp.ut1.ContadorPrimos desde hasta
 * Escribe el resultado en la salida estándar y termina con código 0.
 * Si los argumentos no son válidos, escribe en la salida de error y termina con código 1.
 */
public class ContadorPrimos {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: ContadorPrimos <desde> <hasta>");
            System.exit(1);
        }
        long desde, hasta;
        try {
            desde = Long.parseLong(args[0]);
            hasta = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Los límites deben ser números enteros");
            System.exit(1);
            return;
        }
        long total = 0;
        for (long n = desde; n <= hasta; n++) {
            if (esPrimo(n)) total++;
        }
        System.out.println(total);   // el resultado viaja por la salida estándar
        System.exit(0);              // 0 = todo ha ido bien
    }

    private static boolean esPrimo(long n) {
        if (n < 2) return false;
        if (n % 2 == 0) return n == 2;
        for (long d = 3; d * d <= n; d += 2) {
            if (n % d == 0) return false;
        }
        return true;
    }
}
