// ---------- Ya está hecho ----------
enum class Prioridad { BAJA, MEDIA, ALTA }

interface Describible {
    fun describir(): String
}

abstract class Tarea(val titulo: String, val prioridad: Prioridad) : Describible {
    abstract fun minutosEstimados(): Int
    override fun describir() = "[$prioridad] $titulo (${minutosEstimados()} min)"
}

sealed interface Evento {
    data class Creada(val titulo: String) : Evento
    data class Completada(val titulo: String, val minutosReales: Int) : Evento
    object ListaVaciada : Evento
}

// ---------- Completa ----------
class TareaSimple(titulo: String, prioridad: Prioridad, val minutos: Int) :
    Tarea(titulo, prioridad) {
    override fun minutosEstimados(): Int = TODO()
}

class TareaCompuesta(
    titulo: String,
    prioridad: Prioridad,
    val subtareas: List<Tarea>
) :
    Tarea(titulo, prioridad) {
    override fun minutosEstimados(): Int = TODO()
    override fun describir(): String = TODO()
}

fun urgentes(tareas: List<Tarea>): List<String> = TODO()

fun mensaje(evento: Evento): String = TODO()

// ---------- Comprobaciones (no modificar) ----------
fun main() {
    val t1 = TareaSimple("Diseñar pantalla", Prioridad.ALTA, 60)
    val t2 = TareaSimple("Escribir tests", Prioridad.MEDIA, 30)
    val t3 = TareaCompuesta("Versión 1.0", Prioridad.ALTA, listOf(t1, t2))

    comprobar("t1.minutosEstimados()", 60) { t1.minutosEstimados() }
    comprobar("t3.minutosEstimados()", 90) { t3.minutosEstimados() }
    comprobar("t1.describir()", "[ALTA] Diseñar pantalla (60 min)") {
        t1.describir()
    }
    comprobar("t3.describir()", "[ALTA] Versión 1.0 (90 min, 2 subtareas)") {
        t3.describir()
    }
    comprobar("urgentes", listOf("Diseñar pantalla", "Versión 1.0")) {
        urgentes(listOf(t1, t2, t3))
    }
    comprobar("mensaje(Creada)", "Nueva tarea: Comprar dominio") {
        mensaje(Evento.Creada("Comprar dominio"))
    }
    comprobar("mensaje(Completada)", "Completada: Logo en 45 min") {
        mensaje(Evento.Completada("Logo", 45))
    }
    comprobar("mensaje(ListaVaciada)", "La lista está vacía") {
        mensaje(Evento.ListaVaciada)
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
