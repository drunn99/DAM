import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun descargarNoticias(): List<String> {
    delay(800L)
    return listOf("Kotlin 2.4 publicado", "Nueva versión de Android Studio")
}

suspend fun descargarTiempo(): String {
    delay(1200L)
    return "Aranda de Duero: 18 ºC"
}

suspend fun descargarAvisos(): Int {
    delay(500L)
    return 3
}

fun main() = runBlocking {
    val tiempo = measureTimeMillis {
        val noticias = async { descargarNoticias() }
        val prevision = async { descargarTiempo() }
        val avisos = async { descargarAvisos() }
        println("${noticias.await().size} noticias | ${prevision.await()} | ${avisos.await()} avisos")
    }
    println("Tiempo total: $tiempo ms")
}

/*
 * Antes de async tardará: 2,5s aprox.
 * Después de async tardará: 1,2s aprox
 * */


