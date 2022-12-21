package `20`

import kotlin.math.abs

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val nodes = lines.map { Node1(it.toInt()) }.toList()

    for (i in nodes.indices) {
        nodes[i].next = nodes[(i + 1) % nodes.size]
        nodes[i].prev = nodes[(i + nodes.size - 1) % nodes.size]
    }

    for (node in nodes) {
        val n = node.value

        if (n == 0) {
            continue
        } else {
            for (i in 0 until abs(n)) {
                if (n < 0) {
                    node.shiftBackward()
                } else {
                    node.shiftForward()
                }
            }
        }
    }

    val decrypted = mutableListOf<Node1>()
    var cur: Node1? = nodes[0]
    var i0 = -1
    for (i in nodes.indices) {
        if (cur != null) {
            if (cur.value == 0) {
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

private data class Node1(val value: Int) {
    var next: Node1? = null
    var prev: Node1? = null

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