fun main() {
    for (i in 1..10 step 3) print("$i ")
    println()
    for (i in 10 downTo 1 step 4) print("$i ")
    println()
    for (i in 0 until 3) print("$i ")
    println()

    val x = 7
    val tipo = when {
        x % 2 == 0 -> "par"
        x in 1..9 -> "impar de una cifra"
        else -> "impar"
    }
    println(tipo)

    val y = if (x > 5) {
        println("mayor que 5")
        x * 2
    } else {
        x
    }
    println(y)
    
    println("--- Tabla del 7 ---")
    
    for(i in 10 downTo 1) println("$7 X $i = ${7*i} \n")
    
    println("--- When sin argumento ---")
    val hora: Int = 6
    val saludo = when {
        hora in 6..13 -> "Buenos días"
        hora in 14..20 -> "Buenas tardes"
        hora in 0..5 || hora in 21..24 -> "Buenas noches"
        else -> "Formato de Hora incorrecto, no te saludo :("
    }
    
    println(saludo)
    
}

/*
 * 1 4 7 10
 * 10 6 2
 * 0 1 2
 * impar de una cifra
 * mayor que 5
 * 14
 * */