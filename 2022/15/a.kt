package `15`

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

const val ROW = 2000000

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()

    val lineSegments = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    val sensors = mutableSetOf<Pair<Int, Int>>()
    val beacons = mutableSetOf<Pair<Int, Int>>()

    for (line in lines) {
        val parts = line.substring("Sensor at ".length).split(": closest beacon is at ")
        val sensorParts = parts[0].split(", ")
        val sx = sensorParts[0].substring(2).toInt()
        val sy = sensorParts[1].substring(2).toInt()
        sensors.add(Pair(sx, sy))

        val beaconParts = parts[1].split(", ")
        val bx = beaconParts[0].substring(2).toInt()
        val by = beaconParts[1].substring(2).toInt()
        beacons.add(Pair(bx, by))

        val md = abs(sx - bx) + abs(sy - by)

        if (ROW >= sy - md && ROW <= sy + md) {
            val d = abs(sy - ROW)
            val p1 = Pair(sx - md + d, ROW)
            val p2 = Pair(sx + md - d, ROW)
            lineSegments.add(Pair(p1, p2))
        }
    }

    var combined: Boolean

    do {
        combined = false
        outer@
        for (i in 0 until lineSegments.size - 1) {
            for (j in i + 1 until lineSegments.size) {
                val l1 = lineSegments[i]
                val l2 = lineSegments[j]

                if (!(l1.second.first < l2.first.first || l1.first.first > l2.second.first)) {
                    // Combine into one line segment

                    val minX = min(min(min(l1.first.first, l1.second.first), l2.first.first), l2.second.first)
                    val maxX = max(max(max(l1.first.first, l1.second.first), l2.first.first), l2.second.first)
                    combined = true

                    lineSegments.remove(l1)
                    lineSegments.remove(l2)
                    lineSegments.add(Pair(Pair(minX, l1.first.second), Pair(maxX, l2.first.second)))
                    break@outer
                }
            }
        }

    } while (combined)

    var positions = 0
    for (ls in lineSegments) {
        positions += abs(ls.second.first - ls.first.first) + 1

        for (s in sensors) {
            if (s.second == ROW && s.first >= ls.first.first && s.first <= ls.second.first) {
                positions--
            }
        }

        for (b in beacons) {
            if (b.second == ROW && b.first >= ls.first.first && b.first <= ls.second.first) {
                positions--
            }
        }
    }

    println(positions)
}