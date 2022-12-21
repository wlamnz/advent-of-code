package `20`

import kotlin.math.abs

const val DECRYPTION_KEY = 811589153

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val nodes = lines.map { Node2(it.toLong() * DECRYPTION_KEY) }.toList()

    for (i in nodes.indices) {
        nodes[i].next = nodes[(i + 1) % nodes.size]
        nodes[i].prev = nodes[(i + nodes.size - 1) % nodes.size]
    }

    for (t in 0 until 10) {
        for (node in nodes) {
            val n = node.value
            val toShift = n % (nodes.size - 1)

            if (n == 0L) {
                continue
            } else {
                for (i in 0 until abs(toShift)) {
                    if (n < 0L) {
                        node.shiftBackward()
                    } else {
                        node.shiftForward()
                    }
                }
            }
        }
    }

    val decrypted = mutableListOf<Node2>()
    var cur: Node2? = nodes[0]
    var i0 = -1
    for (i in nodes.indices) {
        if (cur != null) {
            if (cur.value == 0L) {
                i0 = i
            }

            decrypted.add(cur)
        }
        cur = cur?.next
    }

    val n1 = decrypted[(i0 + 1000 + nodes.size) % nodes.size].value
    val n2 = decrypted[(i0 + 2000 + nodes.size) % nodes.size].value
    val n3 = decrypted[(i0 + 3000 + nodes.size) % nodes.size].value

    println(n1 + n2 + n3)
}

private data class Node2(val value: Long) {
    var next: Node2? = null
    var prev: Node2? = null

    fun shiftBackward() {
        val oldPrev = this.prev
        val oldNext = this.next

        oldPrev?.next = oldNext
        oldNext?.prev = oldPrev

        this.prev = oldPrev?.prev
        this.next = oldPrev

        oldPrev?.prev = this
        this?.prev?.next = this
    }

    fun shiftForward() {
        val oldPrev = this.prev
        val oldNext = this.next

        oldPrev?.next = oldNext
        oldNext?.prev = oldPrev

        this.prev = oldNext
        this.next = oldNext?.next

        oldNext?.next = this
        this?.next?.prev = this
    }

}