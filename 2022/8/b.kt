package `8`

import kotlin.math.max

fun main() {
    val map = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    var best = 0
    val dirs = listOf(Pair(-1, 0), Pair(0, -1), Pair(1, 0), Pair(0, 1))

    for (r in 1 until map.size - 1) {
        for (c in 1 until map[0].length - 1) {
            val h = map[r][c]
            var score = 1
            for (dir in dirs) {
                var rr = r + dir.first
                var cc = c + dir.second
                var vd = 0

                while (rr >= 0 && rr < map.size && cc >= 0 && cc < map[0].length) {
                    vd++
                    if (map[rr][cc] >= h) {
                        break
                    }

                    rr += dir.first
                    cc += dir.second
                }

                score *= vd
            }

            best = max(score, best)
        }
    }

    println(best)
}