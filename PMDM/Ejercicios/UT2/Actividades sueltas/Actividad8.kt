// ---------- Ya está hecho ----------
enum class Prioridad { BAJA, MEDIA, ALTA, CRITICA }
/*
 * Al añadir CRITICA al enumerado, no pasa nada al compilar
 * pero es un error que surgirá a posteriori si hacemos un when sobre
 * prioridad, ya que deben ser exhaustivos.
 * */

interface Describible {
    fun describir(): String
}

abstract class Tarea(val titulo: String, val prioridad: Prioridad) : Describible {
    abstract fun minutosEstimados(): Int
    override fun describir() = "[$prioridad] $titulo (${minutosEstimados()} min)"
}

sealed interface Evento {
    data class Creada(val titulo: String) : Evento
    //data class EnCurso(val titulo: String, val minutosReales: Int) : Evento	
    data class Completada(val titulo: String, val minutosReales: Int) : Evento	
    object ListaVaciada : Evento
}

/*
 * Al añadir un nuevo tipo de evento, si no modificamos mensaje() que hace un when sobre
 * los valores de Evento, como este es sealed, se conocen todas sus subclases y por lo tanto
 * nos aparecerá un error de que el when debe ser exhaustivo y agotar todas las opciones.
 * Añadiendo "EnCurso": ERROR - 'when' expression must be exhaustive. Add the 'is EnCurso' branch or an 'else' branch.
 * */

// ---------- Completa ----------
class TareaSimple(titulo: String, prioridad: Prioridad, val minutos: Int) :
    Tarea(titulo, prioridad) {
    override fun minutosEstimados(): Int = minutos
}

class TareaCompuesta(
    titulo: String,
    prioridad: Prioridad,
    val subtareas: List<Tarea>
) :
    Tarea(titulo, prioridad) {
    override fun minutosEstimados(): Int = subtareas.sumOf { it.minutosEstimados() }
    override fun describir(): String = "[$prioridad] $titulo (${minutosEstimados()} min, ${subtareas.size} subtareas)"
}

fun urgentes(tareas: List<Tarea>): List<String> = tareas.filter{it.prioridad == Prioridad.ALTA}.map{it.titulo}

fun mensaje(evento: Evento): String = when (evento) {
    is Evento.Creada -> "Nueva tarea: ${evento.titulo}"
    is Evento.Completada -> "Completada: ${evento.titulo} en ${evento.minutosReales} min"
    Evento.ListaVaciada -> "La lista está vacía"
}

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