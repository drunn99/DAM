// =====================================================================
//  PMDM · UT2 · Práctica 1 — Modelo de clases de una tienda de aplicaciones
//  Alumno/a: Daniel Núñez Uribe
//  Nombre de este fichero al entregar: daniel_nunez_pmdm_ut2_p1.kt
// =====================================================================

// ======================= PARTE 1 =======================
// Categoria, Describible y Valoracion

enum class Categoria (val descripcion: String) {
    JUEGOS("Juegos"),
    PRODUCTIVIDAD("Productividad"),
    EDUCACION("Educación"),
    SALUD("Salud y deporte"),
    MULTIMEDIA("Música y vídeo"),
}

interface Describible {
    fun describir(): String
}

data class Valoracion(val usuario: String, val estrellas: Int, val comentario: String = "") {
    
    init {
        require(usuario.trim().length > 0) {"Debe introducir un usuario"}
        require(estrellas in 1..5) {"Las estrellas deben estar entre 1 y 5"}
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
    

    private val valoraciones = mutableListOf<Valoracion>()
    val numValoraciones: Int
        get() = valoraciones.size

    val mediaValoraciones: Double
        get() = if (valoraciones.size == 0) 0.0 else valoraciones.map{it.estrellas}.average()

   fun valorar(valoracion: Valoracion) {
        valoraciones.removeAll {it.usuario == valoracion.usuario}
        valoraciones.add(valoracion)

   }

    abstract fun precioFinal(): Double

    abstract fun tipo(): String

    override fun describir() : String = "[$id] $nombre · ${categoria.descripcion} · ${tipo()}"
    
}

class AppGratuita(id: String, nombre: String, categoria: Categoria, tamanoMb: Double, val conAnuncios: Boolean) : Aplicacion(id, nombre, categoria, tamanoMb) {
    override fun precioFinal() = 0.0
    
    override fun tipo() = if (!conAnuncios) "Gratuita" else "Gratuita con anuncios"
    
}

class AppDePago(id: String, nombre: String, categoria: Categoria, tamanoMb: Double, val precio: Double) : Aplicacion(id, nombre, categoria, tamanoMb) {
    var descuento: Int = 0
    	set(d) {
            field = d.coerceIn(0,90)
        }
 
    override fun precioFinal() = (precio * (100 - descuento)) / 100
    
    override fun tipo() = "De pago"
    
}

class AppSuscripcion(id: String, nombre: String, categoria: Categoria, tamanoMb: Double, val cuotaMensual: Double, val mesesPrueba: Int = 0) : Aplicacion(id, nombre, categoria, tamanoMb) {
    override fun precioFinal() = (12 - mesesPrueba).coerceAtLeast(0) * cuotaMensual
    
    override fun tipo() = "Suscripción"
    
}

// ======================= PARTE 3 =======================
// ResultadoInstalacion, Dispositivo y mensaje()

sealed interface ResultadoInstalacion {
    data class Instalada(val app: Aplicacion, val espacioLibreMb: Double) : ResultadoInstalacion
    
    data class EspacioInsuficiente(val necesarioMb: Double, val disponibleMb: Double) : ResultadoInstalacion
    
    object YaInstalada : ResultadoInstalacion
    
}

class Dispositivo(val modelo: String, val capacidadMb: Double) {
    private val listaInstaladas = mutableListOf<Aplicacion>()
    
    val espacioLibreMb: Double 
    	get() = capacidadMb - (listaInstaladas.sumOf{it.tamanoMb})
        
    fun instalar(app: Aplicacion): ResultadoInstalacion {
        if (listaInstaladas.any{it.id == app.id}) {
            return ResultadoInstalacion.YaInstalada
        } else if (espacioLibreMb < app.tamanoMb) {
            return ResultadoInstalacion.EspacioInsuficiente(app.tamanoMb, espacioLibreMb)
        } else {
            listaInstaladas.add(app)
            return ResultadoInstalacion.Instalada(app, espacioLibreMb)
        }
    }
    
    fun desinstalar(id: String): Boolean {
        return listaInstaladas.removeAll{it.id == id}
    }
    
    fun aplicacionesInstaladas(): List<Aplicacion> = listaInstaladas.toList()
    
}

fun mensaje(resultado: ResultadoInstalacion): String {
    when(resultado){
        is ResultadoInstalacion.YaInstalada ->return "La aplicación ya estaba instalada"
        is ResultadoInstalacion.EspacioInsuficiente -> return "Sin espacio: necesita ${resultado.necesarioMb} MB y hay ${resultado.disponibleMb} MB"
        is ResultadoInstalacion.Instalada -> return "Instalada: ${resultado.app.nombre} (quedan ${resultado.espacioLibreMb} MB)"
    }
}


// ======================= PARTE 4 =======================
// Tienda y la extension function tamanoTotalMb()

class Tienda(val nombre: String) {
    private val catalogo = mutableListOf<Aplicacion>()
    
    fun publicar(app: Aplicacion): Boolean = if (catalogo.any{it.id == app.id}) false else catalogo.add(app)
    
    fun buscarPorId(id: String): Aplicacion? = catalogo.find {it.id == id}
    
    fun buscar(texto: String): List<Aplicacion> = catalogo.filter{it.nombre.contains(texto, ignoreCase = true)}
    
    fun porCategoria(categoria: Categoria): List<Aplicacion> = catalogo.filter{it.categoria == categoria}
    
    fun filtrar(condicion: (Aplicacion) -> Boolean): List<Aplicacion> = catalogo.filter{condicion(it)}
    
    fun mejorValoradas(n: Int): List<Aplicacion> =  catalogo.filter{it.numValoraciones > 0}.sortedByDescending{it.mediaValoraciones}.take(n)
    
    fun recuentoPorCategoria(): Map<Categoria, Int> = catalogo.groupBy{it.categoria}.mapValues{it.value.size}
    
    fun precioMedioDePago(): Double = catalogo.filterIsInstance<AppDePago>().map{it.precioFinal()}.average()
}

fun List<Aplicacion>.tamanoTotalMb(): Double = this.sumOf{it.tamanoMb}

// ======================= PARTE 5 =======================
fun programaPrincipal() {
    println("======================= PARTE 5 =======================")
    println("=== 5.1 Catálogo ===")
	//R1 y R2
    val pleiEstor = Tienda("pleiEstor")
    val app1 = AppGratuita("M01","Instagram",Categoria.MULTIMEDIA,344.0,false)
    val app2 = AppGratuita("J02","Monster Hunter Now",Categoria.JUEGOS,1245.0,true)
    val app3 = AppDePago("J03","Profesor Layton: La villa misteriosa",Categoria.JUEGOS,266.8,14.99)
    val app4 = AppDePago("E04","Lingodeer",Categoria.EDUCACION,90.0,79.99)
    val app5 = AppSuscripcion("S05","Tinder",Categoria.SALUD, 120.5,9.99,1)
    val app6 = AppSuscripcion("M06","Netflix",Categoria.MULTIMEDIA,524.0,14.99)
    val app7 = AppGratuita("J07","Clash of Clans",Categoria.JUEGOS,344.0,true)
    val app8 = AppGratuita("P08","Habitica",Categoria.PRODUCTIVIDAD,194.0,false)
    val apps = listOf(app1,app2,app3,app4,app5,app6,app7,app8)
    apps.forEach{pleiEstor.publicar(it)}
    
	//R3
    app3.descuento = 10
    app4.descuento = 100
    
    //R4
    apps.forEach{println("${it.describir()} - ${it.precioFinal()}€")}
    
    //R5
    println(pleiEstor.publicar(AppGratuita("M01","Facebook",Categoria.MULTIMEDIA,604.0,false))) //Imprime false
    
    println("=== 5.2 Valoraciones ===")
    //R6
    
  	
    //R7
    
    println("=== 5.3 Consultas ===")
    
    println("=== 5.4 Instalación ===")
}

fun main() {
    programaPrincipal()
    println()
    // Quita las dos barras de cada línea cuando hayas copiado el bloque de esa parte
    comprobacionesParte1()
    comprobacionesParte2()
    comprobacionesParte3()
    comprobacionesParte4()
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
fun comprobacionesParte2() {
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

// ------------------------------ PARTE 3 ------------------------------
fun comprobacionesParte3() {
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

// ------------------------------ PARTE 4 ------------------------------
fun comprobacionesParte4() {
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
