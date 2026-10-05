// ---------- Completa la clase y las funciones ----------
class PlanDatos(val operador: String, val gigasTotales: Double) {

    var gigasConsumidos: Double = 0.0
        private set

    init {
        // TODO: valida con require que gigasTotales sea mayor que 0
    }

    fun consumir(gigas: Double): Boolean = TODO()

    fun restantes(): Double = TODO()

    fun porcentajeUso(): Int = TODO()
}

data class Contacto(
    val nombre: String,
    val telefono: String,
    val favorito: Boolean = false
)

fun marcarFavorito(contacto: Contacto): Contacto = TODO()

fun nombresFavoritos(contactos: List<Contacto>): List<String> = TODO()

// ---------- Comprobaciones (no modificar) ----------
fun main() {
    val plan = PlanDatos("Movistar", 10.0)
    comprobar("consumir(3.5)", true) { plan.consumir(3.5) }
    comprobar("restantes() tras 3.5", 6.5) { plan.restantes() }
    comprobar("consumir(7.0) supera el límite", false) { plan.consumir(7.0) }
    comprobar("restantes() no cambia", 6.5) { plan.restantes() }
    comprobar("porcentajeUso()", 35) { plan.porcentajeUso() }
    comprobar("PlanDatos con 0 GB lanza excepción", true) {
        try { PlanDatos("X", 0.0); false }
        catch (e: IllegalArgumentException) { true }
    }

    val ana = Contacto("Ana", "600111222")
    comprobar("marcarFavorito", Contacto("Ana", "600111222", true)) {
        marcarFavorito(ana)
    }
    comprobar("el original no cambia", false) { ana.favorito }
    val agenda = listOf(Contacto("Raúl", "611", true), ana,
        Contacto("Eva", "622", true))
    comprobar("nombresFavoritos", listOf("Eva", "Raúl")) {
        nombresFavoritos(agenda)
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
