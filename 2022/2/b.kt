package `2`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()

    var total = 0

    for (line in lines) {
        val parts = line.split(" ")
        val p1 = parts[0]
        val p2 = parts[1]
        val score = shapeScore(p1, p2) + outcomeScore(p2)
        total += score
    }

    println(total)
}

private fun outcomeScore(p2: String): Int {
    return when(p2) {
        "X" -> 0
        "Y" -> 3
        "Z" -> 6
        else -> throw Exception("Invalid outcome")
    }
}

private fun shapeScore(p1: String, p2: String): Int {
    if (p1 == "A" && p2 == "Y" || p1 == "B" && p2 == "X" || p1 == "C" && p2 == "Z") {
        return 1
    } else if (p1 == "A" && p2 == "Z" || p1 == "B" && p2 == "Y" || p1 == "C" && p2 == "X") {
        return 2
    }

    return 3
}