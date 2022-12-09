package `9`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val rope = mutableListOf<Pair<Int, Int>>()
    for (i in 1..10) {
        rope.add(Pair(0, 0))
    }

    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(Pair(0, 0))

    for (line in lines) {
        val parts = line.split(" ")
        val dir = parts[0]
        val steps = parts[1].toInt()

        for (s in 1..steps) {
            when (dir) {
                "R" -> rope[0] = Pair(rope[0].first, rope[0].second + 1)
                "L" -> rope[0] = Pair(rope[0].first, rope[0].second - 1)
                "U" -> rope[0] = Pair(rope[0].first - 1, rope[0].second)
                "D" -> rope[0] = Pair(rope[0].first + 1, rope[0].second)
            }

            for (i in 1 until rope.size) {
                val kPrev = rope[i - 1]
                val k = rope[i]

                if (!isTouching(kPrev, k)) {
                    rope[i] = if (kPrev.first == k.first) {
                        Pair(k.first, k.second + if (kPrev.second > k.second) 1 else - 1)
                    } else if (kPrev.second == k.second) {
                        Pair(k.first + if (kPrev.first > k.first) 1 else - 1, k.second)
                    } else {
                        Pair(k.first + if (kPrev.first > k.first) 1 else - 1, k.second + if (kPrev.second > k.second) 1 else - 1)
                    }
                }
            }

            visited.add(rope[9])
        }
    }

    println(visited.size)
}

private fun isTouching(head: Pair<Int, Int>, tail: Pair<Int, Int>): Boolean {
    val x = head.first - tail.first
    val y = head.second - tail.second
    return x * x + y * y <= 2
}