package `18`

const val CUBES_CHECK_DECIDING_INSIDE = 10000

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val positions = mutableSetOf<Triple<Int, Int, Int>>()

    for (line in lines) {
        val parts = line.split(",")
        positions.add(Triple(parts[0].toInt(), parts[1].toInt(), parts[2].toInt()))
    }

    val dir = listOf(-1, 0, 1)
    var surfaceArea = positions.size * 6
    val outside = mutableSetOf<Triple<Int, Int, Int>>()

    for (p in positions) {
        for (dx in dir) {
            for (dy in dir) {
                for (dz in dir) {
                    if (dx != 0 && dy == 0 && dz == 0 || dx == 0 && dy != 0 && dz == 0 || dx == 0 && dy == 0 && dz != 0) {
                        val sp = Triple(p.first + dx, p.second + dy, p.third + dz)
                        if (positions.contains(sp)) {
                            surfaceArea--
                            continue
                        } else if (outside.contains(sp)) {
                            continue
                        }

                        val q = ArrayDeque<Triple<Int, Int, Int>>()
                        val seen = mutableSetOf(sp)
                        q.add(sp)

                        var c = 0
                        while (q.isNotEmpty() && c < CUBES_CHECK_DECIDING_INSIDE) {
                            val next = q.removeFirst()

                            c++

                            for (dx2 in dir) {
                                for (dy2 in dir) {
                                    for (dz2 in dir) {
                                        if (dx2 != 0 && dy2 == 0 && dz2 == 0 || dx2 == 0 && dy2 != 0 && dz2 == 0 || dx2 == 0 && dy2 == 0 && dz2 != 0) {
                                            val new = Triple(next.first + dx2, next.second + dy2, next.third + dz2)

                                            if (outside.contains(new)) {
                                                c = CUBES_CHECK_DECIDING_INSIDE
                                                break
                                            }

                                            if (!positions.contains(new) && !seen.contains(new)) {
                                                q.add(new)
                                                seen.add(new)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (c < CUBES_CHECK_DECIDING_INSIDE) {
                            surfaceArea--
                        } else {
                            outside.addAll(seen)
                            outside.addAll(q)
                        }
                    }
                }
            }
        }
    }

    println(surfaceArea)
}