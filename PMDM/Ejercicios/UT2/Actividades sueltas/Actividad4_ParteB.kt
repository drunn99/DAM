fun main() {
    val a: String? = "Hola"
    val b: String? = null
    println(a!!.length) //Lanza nullPointerException
    println(b?.length) //null sin error
    println(b?.length ?: -1) // -1
    println(b ?: -1) // Lanzaba nullPointerException ahora -1
}