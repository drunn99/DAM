// =====================================================================
//  PMDM · UT2 · Práctica 1 — Modelo de clases de una tienda de aplicaciones
//  Alumno/a: Nombre Apellido
//  Nombre de este fichero al entregar: nombre_apellido_pmdm_ut2_p1.kt
// =====================================================================

// ======================= PARTE 1 =======================
// Categoria, Describible y Valoracion
enum class Categoria (val descripcion: String) {
    JUEGOS("Juegos"),
    PRODUCTIVIDAD("Productividad"),
    EDUCACION("Educacion"),
    SALUD("Salud y deporte"),
    MULTIMEDIA("Multimedia")
}

interface Describible {
    fun describir(): String
}

data class Valoracion(val usuario: String, val estrellas: Int, val comentario: String = "") {
    
    init {
        require(usuario.trim().length > 0) {"Debe introducir un usuario"}
        require(estrellas in 1..5) {"Debe introducir un usuario"}
    }
    
    companion object {
        fun desdeTexto(texto: String): Valoracion? {
            val splitData = texto.split(";").map {it.trim()}
            if(splitData.size in 2..3 && splitData[0].length > 0 && splitData[1].toIntOrNull() ?: 0 in 1..5) {
                val newValoracion: Valoracion = Valoracion(splitData[0],splitData[1].toInt(),splitData.getOrNull(2) ?: "")
                return newValoracion
            } else return null
        }
    }
}

// ======================= PARTE 2 =======================
// Aplicacion, AppGratuita, AppDePago y AppSuscripcion
abstract class Aplicacion(val id: String, val nombre: String, val categoria: Categoria, val tamanoMb: Double) : Describible {
    
    init {
        require(tamanoMb > 0) {"El tamaño de la aplicación debe ser mayor que 0"}
    }
    
    
    private var valoraciones = mutableListOf()<Valoracion>
    val numValoraciones: Int
    	get() = valoraciones.size()
        
    val mediaValoraciones: Double
    	get() = if (valoraciones.size() == 0) 0 else valoraciones.map{it.estrellas}.sum() / valoraciones.size()
    
    

    
    
}
// ======================= PARTE 3 =======================
// ResultadoInstalacion, Dispositivo y mensaje()


// ======================= PARTE 4 =======================
// Tienda y la extension function tamanoTotalMb()


// ======================= PARTE 5 =======================
fun programaPrincipal() {
    // Escribe aquí tu programa principal (apartado 5 del enunciado)
}

fun main() {
    programaPrincipal()
    println()
    // Quita las dos barras de cada línea cuando hayas copiado el bloque de esa parte
    comprobacionesParte1()
    // comprobacionesParte2()
    // comprobacionesParte3()
    // comprobacionesParte4()
}

// ================== COMPROBACIONES (pega aquí los bloques) ==================
// =====================================================================
//  PMDM · UT2 · Práctica 1 — COMPROBACIONES (no modificar)
//  Copia al final de tu fichero el bloque COMÚN y, cuando termines cada
//  parte, el bloque de esa parte. Después llama a su función desde main.
// =====================================================================

// ------------------------------- COMÚN -------------------------------
var totalOk = 0
var totalPruebas = 0

fun comprobar(prueba: String, esperado: Any?, obtenido: () -> Any?) {
    totalPruebas++
    try {
        val valor = obtenido()
        val correcto = if (esperado is Double && valor is Double)
            kotlin.math.abs(esperado - valor) < 0.001 else valor == esperado
        if (correcto) {
            totalOk++
            println("OK         $prueba")
        } else {
            println("FALLO      $prueba -> esperado $esperado, obtenido $valor")
        }
    } catch (e: NotImplementedError) {
        println("PENDIENTE  $prueba")
    } catch (e: Exception) {
        println("ERROR      $prueba -> ${e::class.simpleName}: ${e.message}")
    }
}

fun comprobarExcepcion(prueba: String, accion: () -> Unit) {
    comprobar(prueba, true) {
        try {
            accion()
            false
        } catch (e: IllegalArgumentException) {
            true
        }
    }
}

fun resumen(parte: String, okAntes: Int, pruebasAntes: Int) {
    println("--> $parte: ${totalOk - okAntes} de ${totalPruebas - pruebasAntes} pruebas OK\n")
}

// ------------------------------ PARTE 1 ------------------------------
fun comprobacionesParte1() {
    println("===== PARTE 1 =====")
    val ok = totalOk
    val n = totalPruebas
    comprobar("Categoria.SALUD.descripcion", "Salud y deporte") { Categoria.SALUD.descripcion }
    comprobar("Número de categorías", 5) { Categoria.values().size }
    comprobar("Comentario por defecto", "") { Valoracion("ana", 4).comentario }
    comprobarExcepcion("Valoración con 6 estrellas") { Valoracion("ana", 6) }
    comprobarExcepcion("Valoración sin usuario") { Valoracion("  ", 3) }
    comprobar("desdeTexto con comentario", Valoracion("ana", 4, "Muy útil")) {
        Valoracion.desdeTexto("ana;4;Muy útil")
    }
    comprobar("desdeTexto sin comentario y con espacios", Valoracion("luis", 5)) {
        Valoracion.desdeTexto(" luis ; 5 ")
    }
    comprobar("desdeTexto con estrellas no numéricas", null) { Valoracion.desdeTexto("eva;siete") }
    comprobar("desdeTexto sin separadores", null) { Valoracion.desdeTexto("solo") }
    resumen("Parte 1", ok, n)
}

// ------------------------------ PARTE 2 ------------------------------
/*fun comprobacionesParte2() {
    println("===== PARTE 2 =====")
    val ok = totalOk
    val n = totalPruebas
    val gratis = AppGratuita("ED01", "Duolingo", Categoria.EDUCACION, 180.0, conAnuncios = true)
    val pago = AppDePago("JU01", "Monument Valley", Categoria.JUEGOS, 350.0, precio = 10.0)
    val suscripcion = AppSuscripcion("MU02", "Spotify", Categoria.MULTIMEDIA, 120.0, 5.0, mesesPrueba = 2)
    comprobar("Precio de una app gratuita", 0.0) { gratis.precioFinal() }
    comprobar("Tipo de una gratuita con anuncios", "Gratuita con anuncios") { gratis.tipo() }
    comprobar("Precio de pago con 25 % de descuento", 7.5) {
        pago.descuento = 25
        pago.precioFinal()
    }
    comprobar("Descuento limitado al 90 %", 90) {
        pago.descuento = 150
        pago.descuento
    }
    comprobar("Suscripción: coste del primer año", 50.0) { suscripcion.precioFinal() }
    comprobar("describir()", "[JU01] Monument Valley · Juegos · De pago") { pago.describir() }
    comprobarExcepcion("Tamaño 0 no permitido") { AppGratuita("X", "X", Categoria.JUEGOS, 0.0, false) }
    comprobar("Media sin valoraciones", 0.0) { gratis.mediaValoraciones }
    comprobar("Media con dos valoraciones", 3.0) {
        gratis.valorar(Valoracion("ana", 4))
        gratis.valorar(Valoracion("luis", 2))
        gratis.mediaValoraciones
    }
    comprobar("Un usuario que vuelve a valorar sustituye su valoración", 2) {
        gratis.valorar(Valoracion("ana", 5))
        gratis.numValoraciones
    }
    comprobar("Una aplicación se puede usar como Describible", "[MU02] Spotify · Música y vídeo · Suscripción") {
        val describible: Describible = suscripcion
        describible.describir()
    }
    resumen("Parte 2", ok, n)
}
*/
// ------------------------------ PARTE 3 ------------------------------
/*fun comprobacionesParte3() {
    println("===== PARTE 3 =====")
    val ok = totalOk
    val n = totalPruebas
    val movil = Dispositivo("Pixel 9", 500.0)
    val strava = AppGratuita("SA01", "Strava", Categoria.SALUD, 200.0, false)
    val juego = AppDePago("JU02", "Stardew Valley", Categoria.JUEGOS, 400.0, 5.49)
    comprobar("Instalar con espacio suficiente", ResultadoInstalacion.Instalada(strava, 300.0)) {
        movil.instalar(strava)
    }
    comprobar("Instalar una app ya instalada", ResultadoInstalacion.YaInstalada) { movil.instalar(strava) }
    comprobar("Instalar sin espacio suficiente", ResultadoInstalacion.EspacioInsuficiente(400.0, 300.0)) {
        movil.instalar(juego)
    }
    comprobar("Espacio libre", 300.0) { movil.espacioLibreMb }
    comprobar("Aplicaciones instaladas", 1) { movil.aplicacionesInstaladas().size }
    comprobar("Desinstalar una app instalada", true) { movil.desinstalar("SA01") }
    comprobar("Desinstalar una app que no está", false) { movil.desinstalar("SA01") }
    comprobar("mensaje(Instalada)", "Instalada: Strava (quedan 300.0 MB)") {
        mensaje(ResultadoInstalacion.Instalada(strava, 300.0))
    }
    comprobar("mensaje(EspacioInsuficiente)", "Sin espacio: necesita 400.0 MB y hay 300.0 MB") {
        mensaje(ResultadoInstalacion.EspacioInsuficiente(400.0, 300.0))
    }
    comprobar("mensaje(YaInstalada)", "La aplicación ya estaba instalada") {
        mensaje(ResultadoInstalacion.YaInstalada)
    }
    resumen("Parte 3", ok, n)
}
*/
// ------------------------------ PARTE 4 ------------------------------
/*fun comprobacionesParte4() {
    println("===== PARTE 4 =====")
    val ok = totalOk
    val n = totalPruebas
    val tienda = Tienda("Prueba")
    val a = AppDePago("JU01", "Monument Valley", Categoria.JUEGOS, 350.0, 4.0)
    val b = AppDePago("JU02", "Stardew Valley", Categoria.JUEGOS, 600.0, 6.0)
    val c = AppGratuita("SA01", "Strava", Categoria.SALUD, 210.0, false)
    val d = AppSuscripcion("PR02", "Office Móvil", Categoria.PRODUCTIVIDAD, 400.0, 1.0)
    comprobar("Publicar una app nueva", true) { tienda.publicar(a) }
    comprobar("Publicar un id repetido", false) {
        listOf(b, c, d).forEach { tienda.publicar(it) }
        tienda.publicar(AppGratuita("JU01", "Otra", Categoria.JUEGOS, 1.0, false))
    }
    comprobar("buscarPorId inexistente", null) { tienda.buscarPorId("NO") }
    comprobar("buscar sin distinguir mayúsculas", listOf("Monument Valley", "Stardew Valley")) {
        tienda.buscar("VALLEY").map { it.nombre }
    }
    comprobar("porCategoria(JUEGOS)", 2) { tienda.porCategoria(Categoria.JUEGOS).size }
    comprobar("filtrar con una lambda", listOf("Stardew Valley", "Office Móvil")) {
        tienda.filtrar { it.precioFinal() > 5 }.map { it.nombre }
    }
    comprobar("mejorValoradas", listOf("Strava", "Monument Valley")) {
        a.valorar(Valoracion("ana", 3))
        c.valorar(Valoracion("ana", 5))
        tienda.mejorValoradas(5).map { it.nombre }
    }
    comprobar("recuentoPorCategoria",
        mapOf(Categoria.JUEGOS to 2, Categoria.SALUD to 1, Categoria.PRODUCTIVIDAD to 1)) {
        tienda.recuentoPorCategoria()
    }
    comprobar("precioMedioDePago", 5.0) { tienda.precioMedioDePago() }
    comprobar("tamanoTotalMb (extension function)", 560.0) { listOf(a, c).tamanoTotalMb() }
    resumen("Parte 4", ok, n)
}
*/