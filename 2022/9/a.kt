package `9`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    var head = Pair(0, 0)
    var tail = Pair(0, 0)
    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(tail)

    for (line in lines) {
        val parts = line.split(" ")
        val dir = parts[0]
        val steps = parts[1].toInt()

        for (s in 1..steps) {
            when (dir) {
                "R" -> head = Pair(head.first, head.second + 1)
                "L" -> head = Pair(head.first, head.second - 1)
                "U" -> head = Pair(head.first - 1, head.second)
                "D" -> head = Pair(head.first + 1, head.second)
            }

            if (!isTouching(head, tail)) {
                tail = if (head.first == tail.first) {
                    Pair(tail.first, tail.second + if (head.second > tail.second) 1 else - 1)
                } else if (head.second == tail.second) {
                    Pair(tail.first + if (head.first > tail.first) 1 else - 1, tail.second)
                } else {
                    Pair(tail.first + if (head.first > tail.first) 1 else - 1, tail.second + if (head.second > tail.second) 1 else - 1)
                }
            }

            visited.add(tail)
        }
    }

    println(visited.size)
}

private fun isTouching(head: Pair<Int, Int>, tail: Pair<Int, Int>): Boolean {
    val x = head.first - tail.first
    val y = head.second - tail.second
    return x * x + y * y <= 2
}