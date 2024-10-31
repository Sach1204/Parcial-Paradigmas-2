import kotlin.math.*

// Clase base para operaciones básicas
open class Calculadora {
    open fun sumar(a: Double, b: Double): Double = a + b
    open fun restar(a: Double, b: Double): Double = a - b
    open fun multiplicar(a: Double, b: Double): Double = a * b
    open fun dividir(a: Double, b: Double): Double {
        return try {
            if (b == 0.0) throw ArithmeticException("División por cero")
            a / b
        } catch (e: ArithmeticException) {
            println(e.message)
            Double.NaN
        }
    }
}

// Clase derivada para operaciones científicas
open class CalculadoraCientifica : Calculadora() {
    fun seno(angulo: Double, enRadianes: Boolean = true): Double =
        if (enRadianes) sin(angulo) else sin(Math.toRadians(angulo))

    fun coseno(angulo: Double, enRadianes: Boolean = true): Double =
        if (enRadianes) cos(angulo) else cos(Math.toRadians(angulo))

    fun tangente(angulo: Double, enRadianes: Boolean = true): Double =
        if (enRadianes) tan(angulo) else tan(Math.toRadians(angulo))

    fun potencia(base: Double, exponente: Double): Double = base.pow(exponente)

    fun raiz(numero: Double, indice: Double): Double {
        return if (indice == 0.0) {
            println("Error: El índice no puede ser cero.")
            Double.NaN
        } else numero.pow(1 / indice)
    }

    fun logaritmoBase10(numero: Double): Double = if (numero > 0) log10(numero) else Double.NaN
    fun logaritmoNatural(numero: Double): Double = if (numero > 0) ln(numero) else Double.NaN
    fun exponencial(numero: Double): Double = exp(numero)

    fun gradosARadianes(grados: Double): Double = Math.toRadians(grados)
    fun radianesAGrados(radianes: Double): Double = Math.toDegrees(radianes)
}

// Evaluador de expresiones
class Evaluador(private val calculadora: CalculadoraCientifica) {
    private var posicion = 0
    private var expresion = ""

    fun evaluar(expr: String): Double {
        expresion = expr.replace(" ", "")
        posicion = 0
        return expresionAritmetica()
    }

    private fun expresionAritmetica(): Double {
        var resultado = termino()

        while (posicion < expresion.length) {
            when (expresion[posicion]) {
                '+' -> {
                    posicion++
                    resultado = calculadora.sumar(resultado, termino())
                }
                '-' -> {
                    posicion++
                    resultado = calculadora.restar(resultado, termino())
                }
                ')' -> return resultado
                else -> if (posicion < expresion.length && !expresion[posicion].isDigit() &&
                    expresion[posicion] != '*' && expresion[posicion] != '/') {
                    posicion++
                } else {
                    break
                }
            }
        }
        return resultado
    }

    private fun termino(): Double {
        var resultado = factor()

        while (posicion < expresion.length) {
            when (expresion[posicion]) {
                '*' -> {
                    posicion++
                    resultado = calculadora.multiplicar(resultado, factor())
                }
                '/' -> {
                    posicion++
                    resultado = calculadora.dividir(resultado, factor())
                }
                else -> break
            }
        }
        return resultado
    }

    private fun factor(): Double {
        if (posicion >= expresion.length) return 0.0

        // Manejar paréntesis
        if (expresion[posicion] == '(') {
            posicion++
            val resultado = expresionAritmetica()
            if (posicion < expresion.length && expresion[posicion] == ')') {
                posicion++
            }
            return resultado
        }

        // Manejar funciones trigonométricas
        if (posicion + 3 < expresion.length) {
            when {
                expresion.substring(posicion).startsWith("sin(") -> {
                    posicion += 4
                    val angulo = expresionAritmetica()
                    if (posicion < expresion.length && expresion[posicion] == ')') {
                        posicion++
                    }
                    return calculadora.seno(angulo, true)
                }
                expresion.substring(posicion).startsWith("cos(") -> {
                    posicion += 4
                    val angulo = expresionAritmetica()
                    if (posicion < expresion.length && expresion[posicion] == ')') {
                        posicion++
                    }
                    return calculadora.coseno(angulo, true)
                }
                expresion.substring(posicion).startsWith("tan(") -> {
                    posicion += 4
                    val angulo = expresionAritmetica()
                    if (posicion < expresion.length && expresion[posicion] == ')') {
                        posicion++
                    }
                    return calculadora.tangente(angulo, true)
                }
                expresion.substring(posicion).startsWith("log(") -> {
                    posicion += 4
                    val numero = expresionAritmetica()
                    if (posicion < expresion.length && expresion[posicion] == ')') {
                        posicion++
                    }
                    return calculadora.logaritmoBase10(numero)
                }
                expresion.substring(posicion).startsWith("ln(") -> {
                    posicion += 3
                    val numero = expresionAritmetica()
                    if (posicion < expresion.length && expresion[posicion] == ')') {
                        posicion++
                    }
                    return calculadora.logaritmoNatural(numero)
                }
                expresion.substring(posicion).startsWith("exp(") -> {
                    posicion += 4
                    val numero = expresionAritmetica()
                    if (posicion < expresion.length && expresion[posicion] == ')') {
                        posicion++
                    }
                    return calculadora.exponencial(numero)
                }
                expresion.substring(posicion).startsWith("sqrt(") -> {
                    posicion += 5
                    val numero = expresionAritmetica()
                    if (posicion < expresion.length && expresion[posicion] == ')') {
                        posicion++
                    }
                    return calculadora.raiz(numero, 2.0)
                }
            }
        }

        // Manejar constantes
        when {
            expresion.substring(posicion).startsWith("pi") -> {
                posicion += 2
                return PI
            }
            expresion.substring(posicion).startsWith("e") -> {
                posicion += 1
                return E
            }
        }

        // Manejar números
        return leerNumero()
    }

    private fun leerNumero(): Double {
        val inicio = posicion
        while (posicion < expresion.length &&
            (expresion[posicion].isDigit() || expresion[posicion] == '.' ||
                    (posicion == inicio && expresion[posicion] == '-'))) {
            posicion++
        }
        return expresion.substring(inicio, posicion).toDoubleOrNull() ?: 0.0
    }
}

// Clase con memoria y evaluador de expresiones
class CalculadoraConMemoria : CalculadoraCientifica() {
    private var memoria: Double? = null
    private val evaluador = Evaluador(this)

    fun guardarEnMemoria(valor: Double) {
        memoria = valor
        println("Valor guardado en memoria: $valor")
    }

    fun recuperarMemoria(): Double? {
        return memoria?.also { println("Valor recuperado de memoria: $it") }
    }

    fun limpiarMemoria() {
        memoria = null
        println("Memoria borrada.")
    }

    fun obtenerANS(): Double? = memoria

    fun evaluarExpresion(expresion: String): Double {
        val resultado = evaluador.evaluar(expresion)
        guardarEnMemoria(resultado)
        return resultado
    }
}

fun main() {
    val calculadora = CalculadoraConMemoria()
    var continuar = true

    while (continuar) {
        println("\nSeleccione una operación:")
        println("1. Suma")
        println("2. Resta")
        println("3. Multiplicación")
        println("4. División")
        println("5. Seno")
        println("6. Coseno")
        println("7. Tangente")
        println("8. Potencia")
        println("9. Raíz")
        println("10. Logaritmo base 10")
        println("11. Logaritmo natural")
        println("12. Exponencial")
        println("13. Grados a Radianes")
        println("14. Radianes a Grados")
        println("15. Guardar en Memoria")
        println("16. Recuperar de Memoria")
        println("17. Limpiar Memoria")
        println("18. Evaluar expresión")
        println("19. Salir")

        print("Opción: ")
        val opcion = readLine()?.toIntOrNull()

        when (opcion) {
            1, 2, 3, 4 -> {
                print("Ingrese el primer número (o deje en blanco para usar ANS): ")
                val num1 = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                print("Ingrese el segundo número (o deje en blanco para usar ANS): ")
                val num2 = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue

                val resultado = when (opcion) {
                    1 -> calculadora.sumar(num1, num2)
                    2 -> calculadora.restar(num1, num2)
                    3 -> calculadora.multiplicar(num1, num2)
                    4 -> calculadora.dividir(num1, num2)
                    else -> Double.NaN
                }
                calculadora.guardarEnMemoria(resultado)
                println("Resultado: $resultado")
            }
            5, 6, 7 -> {
                print("Ingrese el ángulo (o deje en blanco para usar ANS): ")
                val angulo = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                print("¿El ángulo está en radianes? (s/n): ")
                val enRadianes = readLine()?.toLowerCase() == "s"

                val resultado = when (opcion) {
                    5 -> calculadora.seno(angulo, enRadianes)
                    6 -> calculadora.coseno(angulo, enRadianes)
                    7 -> calculadora.tangente(angulo, enRadianes)
                    else -> Double.NaN
                }
                calculadora.guardarEnMemoria(resultado)
                println("Resultado: $resultado")
            }
            8 -> {
                print("Ingrese la base (o deje en blanco para usar ANS): ")
                val base = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                print("Ingrese el exponente (o deje en blanco para usar ANS): ")
                val exponente = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                val resultado = calculadora.potencia(base, exponente)
                calculadora.guardarEnMemoria(resultado)
                println("Resultado: $resultado")
            }
            9 -> {
                print("Ingrese el número (o deje en blanco para usar ANS): ")
                val numero = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                print("Ingrese el índice de la raíz (o deje en blanco para usar ANS): ")
                val indice = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                val resultado = calculadora.raiz(numero, indice)
                calculadora.guardarEnMemoria(resultado)
                println("Resultado: $resultado")
            }
            10 -> {
                print("Ingrese el número (o deje en blanco para usar ANS): ")
                val numero = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                val resultado = calculadora.logaritmoBase10(numero)
                calculadora.guardarEnMemoria(resultado)
                println("Resultado: $resultado")
            }
            11 -> {
                print("Ingrese el número (o deje en blanco para usar ANS): ")
                val numero = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                val resultado = calculadora.logaritmoNatural(numero)
                calculadora.guardarEnMemoria(resultado)
                println("Resultado: $resultado")
            }
            12 -> {
                print("Ingrese el número (o deje en blanco para usar ANS): ")
                val numero = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                val resultado = calculadora.exponencial(numero)
                calculadora.guardarEnMemoria(resultado)
                println("Resultado: $resultado")
            }
            13 -> {
                print("Ingrese los grados (o deje en blanco para usar ANS): ")
                val grados = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                val resultado = calculadora.gradosARadianes(grados)
                calculadora.guardarEnMemoria(resultado)
                println("Resultado en radianes: $resultado")
            }
            14 -> {
                print("Ingrese los radianes (o deje en blanco para usar ANS): ")
                val radianes = readLine()?.toDoubleOrNull() ?: calculadora.obtenerANS() ?: continue
                val resultado = calculadora.radianesAGrados(radianes)
                calculadora.guardarEnMemoria(resultado)
                println("Resultado en grados: $resultado")
            }
            15 -> {
                print("Ingrese el valor a guardar en memoria: ")
                val valor = readLine()?.toDoubleOrNull() ?: continue
                calculadora.guardarEnMemoria(valor)
            }
            16 -> {
                val memoria = calculadora.recuperarMemoria()
                if (memoria != null) {
                    println("Valor en memoria: $memoria")
                } else {
                    println("No hay valor en memoria.")
                }
            }
            17 -> {
                calculadora.limpiarMemoria()
            }
            18 -> {
                println("Funciones disponibles: sin(), cos(), tan(), log(), ln(), exp(), sqrt()")
                println("Constantes disponibles: pi, e")
                print("Ingrese la expresión a evaluar: ")
                val expresion = readLine()
                if (expresion != null) {
                    try {
                        val resultado = calculadora.evaluarExpresion(expresion)
                        println("Resultado: $resultado")
                    } catch (e: Exception) {
                        println("Error al evaluar la expresión: ${e.message}")
                    }
                }
            }
            19 -> {
                continuar = false
            }
            else -> println("Opción no válida.")
        }
    }
    println("Calculadora terminada.")
}