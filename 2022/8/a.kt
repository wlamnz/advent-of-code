package `8`

fun main() {
    val map = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    var visibleFromOutside = (map.size * 2 + map[0].length * 2) - 4
    val dirs = listOf(Pair(-1, 0), Pair(0, -1), Pair(1, 0), Pair(0, 1))

    for (r in 1 until map.size - 1) {
        for (c in 1 until map[0].length - 1) {
            val h = map[r][c]
            for (dir in dirs) {
                var rr = r + dir.first
                var cc = c + dir.second
                var visibleDir = true

                while (rr >= 0 && rr < map.size && cc >= 0 && cc < map[0].length) {
                    if (map[rr][cc] >= h) {
                        visibleDir = false
                        break
                    }

                    rr += dir.first
                    cc += dir.second
                }

                if (visibleDir) {
                    visibleFromOutside++
                    break
                }
            }
        }
    }

    println(visibleFromOutside)
}