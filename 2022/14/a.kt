package `14`

import kotlin.math.max
import kotlin.math.min

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val grid = Array(600) { CharArray(600) {'.'} }

    for (line in lines) {
        val points = line.split(" -> ")

        var prevR: Int? = null
        var prevC: Int? = null

        for (p in points) {
            val coordinate = p.split(",")
            val r = coordinate[1].toInt()
            val c = coordinate[0].toInt()

            if (prevR == r && prevC != null) {
                for (i in min(c, prevC)..max(c, prevC)) {
                    grid[r][i] = '#'
                }
            } else if (prevC == c && prevR != null){
                for (i in min(r, prevR)..max(r, prevR)) {
                    grid[i][c] = '#'
                }
            }

            prevR = r
            prevC = c
        }
    }

    var sand = 0

    outer@
    while (true) {
        var sR = 0
        var sC = 500
        var prevSR: Int? = null
        var prevSC: Int? = null

        do {
            var cOffset = 0
            if (grid[sR][sC] == '.') {
                grid[sR][sC] = '+'
            } else if (grid[sR][sC] != '.' && grid[sR][sC - 1] == '.') {
                grid[sR][sC - 1] = '+'
                cOffset--
            }  else if (grid[sR][sC] != '.' && grid[sR][sC + 1] == '.') {
                grid[sR][sC + 1] = '+'
                cOffset++
            } else {
                grid[sR - 1][sC] = 'o'
                break
            }

            if (prevSR != null && prevSC != null) {
                grid[prevSR][prevSC] = '.'
            }

            prevSR = sR
            prevSC = sC + cOffset

            sR++
            sC += cOffset

            if (sR < 0 || sR >= grid.size || sC < 0 || sC >= grid[0].size) {
                break@outer
            }
        } while (true)

        sand++
    }

    println(sand)
}