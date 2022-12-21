package `21`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val expressionByName = mutableMapOf<String, String>()

    for (line in lines) {
        val parts = line.split(": ")
        expressionByName[parts[0]] = parts[1].trim()
    }

    expressionByName["root"] = expressionByName["root"]!!.replace("+", "=")
    expressionByName["root"] = expressionByName["root"]!!.replace("-", "=")
    expressionByName["root"] = expressionByName["root"]!!.replace("*", "=")
    expressionByName["root"] = expressionByName["root"]!!.replace("/", "=")

    var l = 0L
    var r = 100000000000000000L

    while (l < r) {
        val m = l + ((r - l) / 2)
        val res = solve("root", expressionByName, m)

        if (res == 0L) {
            // Answer doesn't appear to be exact? So attempt to get the minimum (which should just be off by one)
            var tmpN = m - 1
            while (solve("root", expressionByName, tmpN) == 0L) {
                tmpN--
            }
            println(tmpN + 1)

            break
        } else if (res > 0L) {
            l = m
        } else {
            r = m
        }
    }
}

private fun solve(name: String, expressionByName: Map<String, String>, input: Long): Long {
    if (name == "humn") {
        return input
    }

    val expression = expressionByName[name]

    try {
        return expression!!.toLong()
    } catch (e: Exception) {
        val parts = expression?.split(" ")
        val op1 = solve(parts!![0], expressionByName, input)
        val op2 = solve(parts!![2], expressionByName, input)

        return when (parts!![1]) {
            "+" -> op1 + op2
            "-" -> op1 - op2
            "*" -> op1 * op2
            "/" -> op1 / op2
            "=" -> op1.minus(op2)
            else -> throw IllegalStateException("Invalid operator")
        }
    }
}