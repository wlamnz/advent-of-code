package `13`

import java.util.*
import kotlin.math.min

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
        .filter { it.isNotBlank() }
        .toList()

    val orderedPackets = mutableListOf<List<*>>()
    val dp1 = listOf(parse("[2]"))
    val dp2 = listOf(parse("[6]"))
    orderedPackets.add(dp1)
    orderedPackets.add(dp2)

    for (i in lines.indices) {
        orderedPackets.add(parse(lines[i]))
    }

    orderedPackets.sortWith{ p1, p2 -> -compare(p1, p2) }

    var i = -1
    var j = -1
    for (p in orderedPackets.indices) {
        if (orderedPackets[p] === dp1) {
            i = p + 1
        } else if (orderedPackets[p] === dp2) {
            j = p + 1
        }
    }

    println(i * j)
}

private fun parse(s: String): List<*> {
    var i = 1

    val p = mutableListOf<Any>()

    while (i < s.length) {
        if (s[i] == '[') {
            val e = i + getClosingBracketLength(s.substring(i))
            p.add(parse(s.substring(i, e)))
            i = e
        } else {
            var e = s.indexOf(',', i)

            if (e == -1) {
                e = s.indexOf(']', i)
            }

            // ""
            if (i != e) {
                p.add(s.substring(i, e).toInt())
            }
            i = e
        }

        i++
    }

    return p
}

private fun compare(p1: List<*>, p2: List<*>): Int {
    val minSize = min(p1.size, p2.size)

    for (i in 0 until minSize) {
        val o1 = p1[i]
        val o2 = p2[i]

        if (o1 is Number && o2 is Number) {
            if (o1.toInt() < o2.toInt()) {
                return 1
            } else if (o1.toInt() > o2.toInt()) {
                return -1
            }
        } else if (o1 is Number) {
            val r = compare(listOf(o1), o2 as List<*>)
            if (r != 0) {
                return r
            }
        } else if (o2 is Number) {
            val r = compare(o1 as List<*>, listOf(o2))
            if (r != 0) {
                return r
            }
        } else {
            val r = compare(o1 as List<*>, o2 as List<*>)
            if (r != 0) {
                return r
            }
        }
    }

    if (p2.size > p1.size) {
        return 1
    } else if (p2.size < p1.size) {
        return -1
    }

    return 0
}

private fun getClosingBracketLength(p: String): Int {
    val s = Stack<Boolean>()
    s.push(true)

    var i = 1
    while (s.isNotEmpty()) {
        if (p[i] == ']') {
            s.pop()
        } else if (p[i] == '[') {
            s.push(true)
        }

        i++
    }

    return i
}


