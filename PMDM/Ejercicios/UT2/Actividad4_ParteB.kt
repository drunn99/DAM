fun main() {
    val a: String? = "Hola"
    val b: String? = null
    println(a!!.length)
    println(b?.length)
    println(b?.length ?: -1)
    println(b!!.length)
}
