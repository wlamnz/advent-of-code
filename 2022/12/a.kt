package `12`

import java.util.PriorityQueue

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val pq = PriorityQueue<State> { s1, s2 -> s1.steps - s2.steps }

    for (r in lines.indices) {
        for (c in lines[r].indices) {
            if (lines[r][c] == 'S') {
                pq.add(State(r, c, 0, 'S', 0))
            }
        }
    }

    val visited = Array(lines.size) { BooleanArray(lines[0].length) }
    val dirs = listOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))

    while (!pq.isEmpty()) {
        val s = pq.poll()

        if (s.id == 'E') {
            println(s.steps)
            break
        }

        if (!visited[s.r][s.c]) {
            visited[s.r][s.c] = true

            for (dir in dirs) {
                val rr = s.r + dir.first
                val cc = s.c + dir.second

                if (rr >= 0 && rr < lines.size && cc >= 0 && cc < lines[0].length) {
                    val c = lines[rr][cc]
                    val h = when (lines[rr][cc]) {
                        'S' -> 0
                        'E' -> 25
                        else -> c - 'a'
                    }

                    if (h <= s.height || s.height + 1 == h) {
                        pq.add(State(rr, cc, h, c, s.steps + 1))
                    }
                }
            }
        }
    }
}