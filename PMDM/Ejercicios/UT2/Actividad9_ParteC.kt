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
        val noticias = descargarNoticias()
        val prevision = descargarTiempo()
        val avisos = descargarAvisos()
        println("${noticias.size} noticias | $prevision | $avisos avisos")
    }
    println("Tiempo total: $tiempo ms")
}
