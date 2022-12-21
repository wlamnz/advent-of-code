package `21`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val expressionByName = mutableMapOf<String, String>()

    for (line in lines) {
        val parts = line.split(": ")
        expressionByName[parts[0]] = parts[1].trim()
    }

    println(solve("root", expressionByName))
}

private fun solve(name: String, expressionByName: Map<String, String>): Long {
    val expression = expressionByName[name]

    try {
        return expression!!.toLong()
    } catch (e: Exception) {
        val parts = expression?.split(" ")
        val op1 = solve(parts!![0], expressionByName)
        val op2 = solve(parts!![2], expressionByName)

        return when (parts!![1]) {
            "+" -> op1 + op2
            "-" -> op1 - op2
            "*" -> op1 * op2
            "/" -> op1 / op2
            else -> throw IllegalStateException("Invalid operator")
        }
    }
}