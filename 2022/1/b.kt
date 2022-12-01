package `1`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()

    val calories = mutableListOf<Int>()
    var curCalories = 0

    for (line in lines) {
        if (line.isBlank()) {
            calories.add(curCalories)
            curCalories = 0
        } else {
            curCalories += Integer.parseInt(line)
        }
    }
    calories.add(curCalories)
    calories.sortDescending()

    println(calories[0] + calories[1] + calories[2])
}