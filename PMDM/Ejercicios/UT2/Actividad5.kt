// ---------- Completa las funciones ----------
fun operar(a: Int, b: Int, operacion: (Int, Int) -> Int): Int = TODO()

fun aplicarNVeces(n: Int, valorInicial: Int, f: (Int) -> Int): Int = TODO()

fun crearMultiplicador(factor: Int): (Int) -> Int = TODO()

fun contarQueCumplen(rango: IntRange, condicion: (Int) -> Boolean): Int = TODO()

fun fichaApp(nombre: String, version: String): String = TODO()

// ---------- Comprobaciones (no modificar) ----------
fun main() {
    comprobar("operar(6, 3) suma", 9) { operar(6, 3) { x, y -> x + y } }
    comprobar("operar(6, 3) producto", 18) { operar(6, 3) { x, y -> x * y } }
    comprobar("aplicarNVeces(3, 2)", 16) { aplicarNVeces(3, 2) { it * 2 } }
    comprobar("aplicarNVeces(0, 5)", 5) { aplicarNVeces(0, 5) { it + 1 } }
    comprobar("crearMultiplicador(3)(7)", 21) { crearMultiplicador(3)(7) }
    comprobar("pares en 1..10", 5) { contarQueCumplen(1..10) { it % 2 == 0 } }
    comprobar("múltiplos de 7", 14) { contarQueCumplen(1..100) { it % 7 == 0 } }
    comprobar("fichaApp", "App: Strava | Versión: 2.4") {
        fichaApp("Strava", "2.4")
    }
}

// ---------- Función de comprobación (no modificar) ----------
fun comprobar(prueba: String, esperado: Any?, obtenido: () -> Any?) {
    try {
        val valor = obtenido()
        val correcto = if (esperado is Double && valor is Double)
            kotlin.math.abs(esperado - valor) < 0.001 else valor == esperado
        if (correcto) println("OK         $prueba")
        else println("FALLO      $prueba -> esperado $esperado, obtenido $valor")
    } catch (e: NotImplementedError) {
        println("PENDIENTE  $prueba")
    }
}
