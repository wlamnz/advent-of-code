package `17`

import kotlin.math.max

fun main() {
    val line = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLine()

    var highestRock = 0
    var shapeIndex = 0
    var gasIndex = 0
    val allRocks = mutableSetOf<Pair<Int, Int>>()

    for (i in 0 until 2022) {
        var shape = spawnShape(shapeIndex, highestRock)

        while (true) {
            val dx = line[gasIndex]
            val shapeAfterDx = when (dx) {
                '<' -> moveLeft(shape)
                '>' -> moveRight(shape)
                else -> throw IllegalStateException("Invalid left or right direction")
            }

            var xCollide = false
            for (pair in shapeAfterDx) {
                if (allRocks.contains(pair)) {
                    xCollide = true
                    break
                }
            }

            if (!xCollide) {
                shape = shapeAfterDx
            }

            gasIndex++
            gasIndex %= line.length

            val shapeAfterMovingDown = moveDown(shape)
            var yCollide = false

            for (pair in shapeAfterMovingDown) {
                if (allRocks.contains(pair) || pair.first < 0) {
                    yCollide = true
                    break
                }
            }

            if (yCollide) {
                for (pair in shape) {
                    highestRock = max(highestRock, pair.first + 1)
                }

                allRocks.addAll(shape)
                break
            }

            shape = shapeAfterMovingDown
        }

        shapeIndex++
        shapeIndex %= 5
    }

    println(highestRock)
}

private fun spawnShape(shapeIndex: Int, highestRock: Int): List<Pair<Int, Int>> {
    val threeUnitsAboveRock = highestRock + 3
    val twoUnitsFromLeft = 2
    if (shapeIndex == 0) {
        val shape = mutableListOf<Pair<Int, Int>>()
        for (x in twoUnitsFromLeft until twoUnitsFromLeft + 4) {
            shape.add(Pair(threeUnitsAboveRock, x))
        }

        return shape
    } else if (shapeIndex == 1) {
        val shape = mutableListOf<Pair<Int, Int>>()
        for (x in twoUnitsFromLeft until twoUnitsFromLeft + 3) {
            shape.add(Pair(threeUnitsAboveRock + 1, x))
        }

        shape.add(Pair(threeUnitsAboveRock, twoUnitsFromLeft + 1))
        shape.add(Pair(threeUnitsAboveRock + 2, twoUnitsFromLeft + 1))

        return shape
    } else if (shapeIndex == 2) {
        val shape = mutableListOf<Pair<Int, Int>>()
        for (x in twoUnitsFromLeft until twoUnitsFromLeft + 3) {
            shape.add(Pair(threeUnitsAboveRock, x))
        }

        shape.add(Pair(threeUnitsAboveRock + 1, twoUnitsFromLeft + 2))
        shape.add(Pair(threeUnitsAboveRock + 2,  twoUnitsFromLeft + 2))

        return shape
    } else if (shapeIndex == 3) {
        val shape = mutableListOf<Pair<Int, Int>>()
        for (y in threeUnitsAboveRock until threeUnitsAboveRock + 4) {
            shape.add(Pair(y, twoUnitsFromLeft))
        }

        return shape
    } else {
        val shape = mutableListOf<Pair<Int, Int>>()
        shape.add(Pair(threeUnitsAboveRock, twoUnitsFromLeft))
        shape.add(Pair(threeUnitsAboveRock + 1, twoUnitsFromLeft))
        shape.add(Pair(threeUnitsAboveRock, twoUnitsFromLeft + 1))
        shape.add(Pair(threeUnitsAboveRock + 1, twoUnitsFromLeft + 1))

        return shape
    }
}

private fun moveLeft(shape: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val newShape = mutableListOf<Pair<Int, Int>>()

    for (p in shape) {
        if (p.second - 1 < 0) {
            // Cannot move left
            return shape
        }
        newShape.add(Pair(p.first, p.second - 1))
    }

    return newShape
}

private fun moveRight(shape: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val newShape = mutableListOf<Pair<Int, Int>>()

    for (p in shape) {
        if (p.second + 1 > 6) {
            // Cannot move right
            return shape
        }
        newShape.add(Pair(p.first, p.second + 1))
    }

    return newShape
}

private fun moveDown(shape: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val newShape = mutableListOf<Pair<Int, Int>>()

    for (p in shape) {
        newShape.add(Pair(p.first - 1, p.second))
    }

    return newShape
}