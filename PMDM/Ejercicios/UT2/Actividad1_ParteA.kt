fun main() {
    val modelo = "Galaxy S25"
    val precio = 899
    var stock: Int = 10
    val descuento = 0.15

    modelo = "Galaxy S25 Ultra"
    val precioFinal: Int = precio - precio * descuento
    stock = stock - 1
    val tieneStock: Boolean = stock
    val codigo: Long = stock

    println("El " + modelo + " cuesta " + precioFinal + " euros")
    println("Quedan $stock.unidades unidades")
}

//CORRECCIÓN
fun main() {
    //val modelo = "Galaxy S25" Se cambia su valor después, debe ser var
    var modelo = "Galaxy S25"
    val precio = 899
    var stock: Int = 10
    val descuento = 0.15

    modelo = "Galaxy S25 Ultra"
    //val precioFinal: Int = precio - precio * descuento Espera  valor entero, cálculo devuelve Double, quitamos tipo para que lo infiera
    val precioFinal = precio - precio * descuento
    stock = stock - 1
    //val tieneStock: Boolean = stock Espera valor booleano, introducimos un entero, cambio por comprobación
    val tieneStock: Boolean = stock > 0 
    val codigo: Long = stock.toLong() //Espera tipo Long, introducimos un entero, cambio por casteo.

    println("El $modelo cuesta $precioFinal euros")
    println("Quedan $stock unidades unidades")
}
