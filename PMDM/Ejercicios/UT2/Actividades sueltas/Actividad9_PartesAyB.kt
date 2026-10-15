import kotlinx.coroutines.*

fun main() = runBlocking {
    println("1. Inicio")
    Thread.sleep(200)
        println("2. Tarea A")
    Thread.sleep(100)
        println("3. Tarea B")
    println("4. Fin del bloque principal")
}

/*
 * Orden de ejecución:
 * 1.Inicio -> 4. Fin del bloque principal -> 3. Tarea B -> 2. Tarea A
 * Tardará aprox 200ms (Un poco más)
 * 
 * Orden de ejecución:
 * 1. Inicio -> 2. Tarea A -> 3. Tarea B -> 4. Fin del bloque principal
 * Cambia el orden porque el hilo se detiene por completo con Thread.Sleep, en el ejemplo anterior solo retrasamos
 * la ejecución del bloque del delay, pero el hilo no se detiene. En una APP Android, jamás debemos parar el hilo.
 * 
 * */