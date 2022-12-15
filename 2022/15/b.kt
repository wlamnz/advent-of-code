package `15`

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()

    for (ii in 0..4000000) {
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


            if (ii >= sy - md && ii <= sy + md) {
                val d = abs(sy - ii)
                val p1 = Pair(sx - md + d, ii)
                val p2 = Pair(sx + md - d, ii)
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

                    if (l1.first.second == l2.first.second && !(l1.second.first < l2.first.first || l1.first.first > l2.second.first)) {
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

        if (lineSegments.size == 2) {
            val l1 = lineSegments[0]
            val l2 = lineSegments[1]

            val x = (if (l2.first.first > l1.second.first) l1.second.first + 1 else l2.second.first + 1).toLong()
            println(x * 4000000 + l1.first.second)
            break
        }
    }
}