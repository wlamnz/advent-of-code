package `1`

import kotlin.math.max

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()

    var mostCalories = 0
    var curCalories = 0

    for (line in lines) {
        if (line.isBlank()) {
            mostCalories = max(mostCalories, curCalories)
            curCalories = 0
        } else {
            curCalories += Integer.parseInt(line)
        }
    }
    mostCalories = max(mostCalories, curCalories)

    println(mostCalories)
}
