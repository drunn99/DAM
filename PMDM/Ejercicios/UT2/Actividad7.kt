// ---------- Completa la clase y las funciones ----------
class PlanDatos(val operador: String, val gigasTotales: Double) {

    var gigasConsumidos: Double = 0.0
        private set
    
     /*
     * 
     * gigasConsumidos es private set para evitar
     * que se pueda modificar desde fuera de su propia clase;
     * lo que abriría la puerta a exploits donde el usuario modifique
     * sus propios gigas consumidos.
     * 
     * */

    init {
        require(gigasTotales > 0) {"Los gigas totales han de ser mayor que 0"}
    }

    fun consumir(gigas: Double): Boolean {
        if(gigasConsumidos + gigas <= gigasTotales) {
            gigasConsumidos+= gigas
            return true
        } else {
            return false
        }
    }

    fun restantes(): Double = gigasTotales - gigasConsumidos

    fun porcentajeUso(): Int = ((gigasConsumidos / gigasTotales) * 100).toInt()
    
}

data class Contacto(
    val nombre: String,
    val telefono: String,
    val favorito: Boolean = false
)

fun marcarFavorito(contacto: Contacto): Contacto = contacto.copy(favorito = true)

fun nombresFavoritos(contactos: List<Contacto>): List<String> = contactos.filter{it.favorito}.map{it.nombre}.sorted()



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
