package `4`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()

    var count = 0
    for (line in lines) {
        val ranges = line.split(",")
        val r1 = ranges[0].split("-").map { it.toInt() }
        val r2 = ranges[1].split("-").map { it.toInt() }

        if ((r1[0] >= r2[0] && r1[1] <= r2[1]) || (r2[0] >= r1[0] && r2[1] <= r1[1])) {
            count++
        }
    }

    println(count)
}