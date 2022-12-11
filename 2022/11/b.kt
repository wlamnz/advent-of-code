package `11`

import java.util.function.LongFunction
import java.util.function.LongPredicate

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val monkeys = mutableListOf<Monkey>()

    var i = 0
    var cm = 1.toLong()
    while (i < lines.size) {
        if (lines[i].startsWith("Monkey")) {
            val items = ArrayDeque(lines[i + 1].split(": ")[1].split(", ").map { it.toLong() }.toList())
            val parts = lines[i + 2].split(" = ")[1].split(" ")
            val operation = LongFunction {
                val v1 = if (parts[0] == "old") it else parts[0].toLong()
                val v2 = if (parts[2] == "old") it else parts[2].toLong()
                val v3 = if (parts[1] == "*") v1 * v2 else v1 + v2
                v3 % cm
            }
            val divisibleVal = lines[i + 3].split(" ").last().toLong()
            cm *= divisibleVal
            val test = LongPredicate { it % divisibleVal == 0.toLong() }
            val idToThrowIfTrue = lines[i + 4].split(" ").last().toInt()
            val idToThrowIfFalse = lines[i + 5].split(" ").last().toInt()

            monkeys.add(Monkey(monkeys.size, items, operation, test, idToThrowIfTrue, idToThrowIfFalse))
            i+=5
        }

        i++
    }

    val inspectedItems = LongArray(monkeys.size)

    for (r in 1..10000) {
        for (monkey in monkeys) {
            while (!monkey.items.isEmpty()) {
                inspectedItems[monkey.id]++
                val item = monkey.items.removeFirst()
                val worryLevel = monkey.operation.apply(item)

                if (monkey.test.test(worryLevel)) {
                    monkeys[monkey.idToThrowIfTrue].items.add(worryLevel)
                } else {
                    monkeys[monkey.idToThrowIfFalse].items.add(worryLevel)
                }
            }
        }
    }

    inspectedItems.sortDescending()
    println(inspectedItems[0] * inspectedItems[1])
}