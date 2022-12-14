package `14`

import kotlin.math.max
import kotlin.math.min

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val grid = Array(600) { CharArray(1200) {'.'} }

    var maxY = 0

    for (line in lines) {
        val points = line.split(" -> ")

        var prevR: Int? = null
        var prevC: Int? = null

        for (p in points) {
            val coordinate = p.split(",")
            val r = coordinate[1].toInt()
            val c = coordinate[0].toInt()
            maxY = max(r, maxY)

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

    maxY += 2

    for (i in grid[0].indices) {
        grid[maxY][i] = '#'
    }

    var sand = 0

    outer@
    while (true) {
        var sR = 0
        var sC = 500
        var prevSR: Int? = null
        var prevSC: Int? = null

        do {
            if (grid[0][500] == 'o') {
                break@outer
            }

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
        } while (true)

        sand++
    }

    println(sand)
}