// ---------- Completa las funciones (sin bucles for) ----------
fun empiezanPor(letra: Char, apps: List<String>): List<String> = TODO()

fun longitudes(apps: List<String>): List<Int> = TODO()

fun totalDescargas(descargas: Map<String, Int>): Int = TODO()

fun masDescargada(descargas: Map<String, Int>): String? = TODO()

fun conAlMenos(descargas: Map<String, Int>, minimo: Int): List<String> = TODO()

fun agruparPorInicial(apps: List<String>): Map<Char, List<String>> = TODO()

fun resumen(apps: List<String>, n: Int): String = TODO()

// ---------- Comprobaciones (no modificar) ----------
fun main() {
    val apps = listOf("WhatsApp", "Instagram", "Duolingo", "Strava",
        "Spotify", "Waze", "TikTok")
    val descargas = mapOf("WhatsApp" to 5000, "Instagram" to 3000,
        "Duolingo" to 500, "Strava" to 100, "Spotify" to 1000,
        "Waze" to 500, "TikTok" to 2000)

    comprobar("empiezanPor", listOf("Strava", "Spotify")) {
        empiezanPor('S', apps)
    }
    comprobar("longitudes", listOf(8, 9, 8, 6, 7, 4, 6)) { longitudes(apps) }
    comprobar("totalDescargas", 12100) { totalDescargas(descargas) }
    comprobar("masDescargada", "WhatsApp") { masDescargada(descargas) }
    comprobar("masDescargada(vacío)", null) { masDescargada(emptyMap()) }
    val esperado = listOf("Instagram", "Spotify", "TikTok", "WhatsApp")
    comprobar("conAlMenos", esperado) {
        conAlMenos(descargas, 1000)
    }
    comprobar("agruparPorInicial", listOf("WhatsApp", "Waze")) {
        agruparPorInicial(apps)['W']
    }
    comprobar("resumen(3)", "WHATSAPP, INSTAGRAM, DUOLINGO") { resumen(apps, 3) }
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
