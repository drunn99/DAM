// ---------- Completa las funciones ----------
fun precioConIva(precioBase: Double, iva: Double = 0.21): Double = precioBase * (1 + iva)

fun calificacion(nota: Double): String {
    return when {
        nota < 0.0 || nota > 10.0 -> "Nota no válida"
        nota < 5.0 -> "Suspenso"
        5.0 - nota == 0.0 -> "Aprobado"
        nota < 7.0 -> "Bien"
        nota < 9.0 -> "Notable"
        else -> "Sobresaliente"
    }
}

fun esBisiesto(anio: Int): Boolean = (anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0;

fun fizzBuzz(n: Int): String = if(n % 15 == 0) return "FizzBuzz" else if (n % 5 == 0) return "Buzz" else if (n % 3 == 0) return "Fizz" else return n.toString()

fun Int.esPrimo(): Boolean {
    for (i in 2 until this/2) {
        if (this % i == 0) return false
    }
    
    return if (this == 1) false else true
}

fun String.contarVocales(): Int {
    val vocales = arrayOf('a','e','i','o','u')
    var count: Int = 0
    
    for(char in this) {
		if(vocales.contains(char.lowercaseChar())){
            count++
        }
    }
    
    return count
}

// ---------- Comprobaciones (no modificar) ----------
fun main() {
    comprobar("precioConIva(100.0)", 121.0) { precioConIva(100.0) }
    comprobar("precioConIva(50.0, 0.10)", 55.0) { precioConIva(50.0, iva = 0.10) }
    comprobar("calificacion(4.99)", "Suspenso") { calificacion(4.99) }
    comprobar("calificacion(5.0)", "Aprobado") { calificacion(5.0) }
    comprobar("calificacion(6.5)", "Bien") { calificacion(6.5) }
    comprobar("calificacion(8.9)", "Notable") { calificacion(8.9) }
    comprobar("calificacion(10.0)", "Sobresaliente") { calificacion(10.0) }
    comprobar("calificacion(11.0)", "Nota no válida") { calificacion(11.0) }
    comprobar("esBisiesto(2024)", true) { esBisiesto(2024) }
    comprobar("esBisiesto(1900)", false) { esBisiesto(1900) }
    comprobar("esBisiesto(2000)", true) { esBisiesto(2000) }
    comprobar("esBisiesto(2026)", false) { esBisiesto(2026) }
    comprobar("fizzBuzz(9)", "Fizz") { fizzBuzz(9) }
    comprobar("fizzBuzz(10)", "Buzz") { fizzBuzz(10) }
    comprobar("fizzBuzz(15)", "FizzBuzz") { fizzBuzz(15) }
    comprobar("fizzBuzz(7)", "7") { fizzBuzz(7) }
    comprobar("7.esPrimo()", true) { 7.esPrimo() }
    comprobar("1.esPrimo()", false) { 1.esPrimo() }
    comprobar("21.esPrimo()", false) { 21.esPrimo() }
    comprobar("2.esPrimo()", true) { 2.esPrimo() }
    comprobar("contarVocales()", 6) { "Android Studio".contarVocales() }
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

