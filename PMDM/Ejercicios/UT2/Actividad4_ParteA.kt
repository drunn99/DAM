// ---------- Completa las funciones (sin usar !!) ----------
fun longitud(texto: String?): Int = TODO()

fun saludo(nombre: String?): String = TODO()

fun parsearEdad(texto: String): Int? = TODO()

fun primeraLetra(texto: String?): Char? = TODO()

fun sumaSegura(a: String?, b: String?): Int = TODO()

// ---------- Comprobaciones (no modificar) ----------
fun main() {
    comprobar("longitud(null)", 0) { longitud(null) }
    comprobar("longitud(\"Kotlin\")", 6) { longitud("Kotlin") }
    comprobar("saludo(\"Ana\")", "Hola, Ana") { saludo("Ana") }
    comprobar("saludo(null)", "Hola, invitado") { saludo(null) }
    comprobar("saludo(\"   \")", "Hola, invitado") { saludo("   ") }
    comprobar("parsearEdad(\"25\")", 25) { parsearEdad("25") }
    comprobar("parsearEdad(\"abc\")", null) { parsearEdad("abc") }
    comprobar("parsearEdad(\"150\")", null) { parsearEdad("150") }
    comprobar("parsearEdad(\"-3\")", null) { parsearEdad("-3") }
    comprobar("primeraLetra(\"kotlin\")", 'K') { primeraLetra("kotlin") }
    comprobar("primeraLetra(null)", null) { primeraLetra(null) }
    comprobar("primeraLetra(\"\")", null) { primeraLetra("") }
    comprobar("sumaSegura(\"10\", \"5\")", 15) { sumaSegura("10", "5") }
    comprobar("sumaSegura(\"10\", null)", 10) { sumaSegura("10", null) }
    comprobar("sumaSegura(\"x\", \"7\")", 7) { sumaSegura("x", "7") }
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
