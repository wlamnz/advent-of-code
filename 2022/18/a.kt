package `18`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val positions = mutableSetOf<Triple<Int, Int, Int>>()

    for (line in lines) {
        val parts = line.split(",")
        positions.add(Triple(parts[0].toInt(), parts[1].toInt(), parts[2].toInt()))
    }

    val dir = listOf(-1, 0, 1)
    var surfaceArea = positions.size * 6

    for (p in positions) {
        for (dx in dir) {
            for (dy in dir) {
                for (dz in dir) {
                    if (dx != 0 && dy == 0 && dz == 0 || dx == 0 && dy != 0 && dz == 0 || dx == 0 && dy == 0 && dz != 0) {
                        if (positions.contains(Triple(p.first + dx, p.second + dy, p.third + dz))) {
                            surfaceArea--
                        }
                    }
                }
            }
        }
    }

    println(surfaceArea)
}