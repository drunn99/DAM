import kotlinx.coroutines.*

fun main() = runBlocking {
    println("1. Inicio")
    launch {
        delay(200L)
        println("2. Tarea A")
    }
    launch {
        delay(100L)
        println("3. Tarea B")
    }
    println("4. Fin del bloque principal")
}
