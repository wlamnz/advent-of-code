package `2`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()

    var total = 0

    for (line in lines) {
        val parts = line.split(" ")
        val p1 = parts[0]
        val p2 = parts[1]
        val score = shapeScore(p2) + outcomeScore(p1, p2)
        total += score
    }

    println(total)
}

private fun outcomeScore(p1: String, p2: String): Int {
    if (p1 == "A" && p2 == "X" || p1 == "B" && p2 == "Y" || p1 == "C" && p2 == "Z") {
        return 3
    } else if (p1 == "C" && p2 == "X" || p1 == "A" && p2 == "Y" || p1 == "B" && p2 == "Z") {
        return 6
    }

    return 0
}

private fun shapeScore(p2: String): Int {
    return when(p2) {
        "X" -> 1
        "Y" -> 2
        "Z" -> 3
        else -> throw Exception("Invalid shape")
    }
}