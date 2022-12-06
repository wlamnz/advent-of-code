package `6`

import java.util.LinkedList

fun main() {
    val line = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()[0]
    val window = LinkedList<Char>()
    val seen = mutableSetOf<Char>()

    for (i in line.indices) {
        val c = line[i]
        if (seen.contains(c)) {
            while (!window.isEmpty()) {
                val removed = window.poll()
                seen.remove(removed)

                if (removed == c) {
                    break
                }
            }
        }

        window.add(c)
        seen.add(c)

        if (window.size == 14) {
            println(i + 1)
            break
        }
    }
}