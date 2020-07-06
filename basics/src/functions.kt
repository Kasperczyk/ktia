import java.util.*

fun max(a: Int, b: Int): Int {
    return if (a > b) a else b // block body
}

fun max2(a: Int, b: Int): Int = if (a > b) a else b // expression body

fun max3(a: Int, b: Int) = if (a > b) a else b // return type inferred

fun greet(name: String) {
    println("Hello, $name!") // string template
    println("HELLO, ${name.toUpperCase()}!")
}

class Person(
    val name: String,
    var isMarried: Boolean
)

class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() = height == width
}

enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    ORANGE(255, 165, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    INDIGO(75, 0, 130),
    VIOLET(238, 130, 238); // the only place where a semicolon is mandatory

    fun rgb() = (r * 256 + g) * 256 + b
}

fun getMnemonic(color: Color) =
    when (color) {
        Color.RED -> "Richard"
        Color.ORANGE -> "Of"
        Color.YELLOW -> "York"
        Color.GREEN -> "Gave"
        Color.BLUE -> "Battle"
        Color.INDIGO -> "In"
        Color.VIOLET -> "Vain"
    }

fun getWarmth(color: Color) =
    when (color) {
        Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
        Color.GREEN -> "neutral"
        Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
    }

fun mix(c1: Color, c2: Color) =
    when (setOf(c1, c2)) {
        setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
        setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
        setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO
        else -> throw Exception("Dirty Color")
    }

// harder to read but doesn't create any extra objects
fun mixOptimized(c1: Color, c2: Color) =
    when {
        (c1 == Color.RED && c2 == Color.YELLOW) || (c1 == Color.YELLOW && c2 == Color.RED) -> Color.ORANGE
        (c1 == Color.YELLOW && c2 == Color.BLUE) || (c1 == Color.BLUE && c2 == Color.YELLOW) -> Color.GREEN
        (c1 == Color.BLUE && c2 == Color.VIOLET) || (c1 == Color.VIOLET && c2 == Color.BLUE) -> Color.INDIGO
        else -> throw Exception("Dirty Color")
    }

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(expr: Expr): Int =
    // smart casts (explicit cast: val n = expr as Num)
    when (expr) {
        is Num -> expr.value
        is Sum -> eval(expr.left) + eval(expr.right)
        else -> throw IllegalArgumentException("Unknown expression")
    }

fun evalWithLogging(expr: Expr): Int =
    // smart casts (explicit cast: val n = expr as Num)
    when (expr) {
        is Num -> {
            println("num: ${expr.value}")
            expr.value
        }
        is Sum -> {
            val left = evalWithLogging(expr.left)
            val right = evalWithLogging(expr.right)
            println("sum: $left + $right")
            left + right
        }
        else -> throw IllegalArgumentException("Unknown expression")
    }

fun fizzBuzz(i: Int) =
    when {
        i % 15 == 0 -> "FizzBuzz "
        i % 3 == 0 -> "Fizz "
        i % 5 == 0 -> "Buzz "
        else -> "$i "
    }

fun loopExamples() {
    val binaryReps = TreeMap<Char, String>()
    for (char in 'A'..'F') {
        val binary = Integer.toBinaryString(char.toInt())
        binaryReps[char] = binary
    }
    for ((letter, binary) in binaryReps) {
        println("$letter = $binary")
    }

    val list = arrayListOf("10", "11", "1001")
    for ((index, element) in list.withIndex()) {
        println("$index: $element")
    }
}

fun isLetter(char: Char) = char in 'a'..'z' || char in 'A'..'Z'
fun isNotDigit(char: Char) = char !in '0'..'9'

fun recognize(char: Char): String =
    when (char) {
        in '0'..'9' -> "It's a digit!"
        in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
        else -> "I don't know..."
    }


fun main() {
    println(max(1, 2))
    println(max2(3, 2))
    greet("Rene")
    println(Color.BLUE.rgb())
    println(getMnemonic(Color.BLUE))
    println(getWarmth(Color.ORANGE))
    println(eval(Sum(Sum(Num(1), Num(2)), Num(4))))
    println(evalWithLogging(Sum(Sum(Num(1), Num(2)), Num(4))))
    println("---------------------------")
    for(i in 1..100) {
        print(fizzBuzz(i))
    }
    println("\n---------------------------")
    for (i in 100 downTo 1 step 2) {
        print(fizzBuzz(i))
    }
    println("\n---------------------------")
    println(loopExamples())
    println(isLetter('q'))
    println(isNotDigit('x'))
    println(recognize('8'))
}