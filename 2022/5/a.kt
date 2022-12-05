package `5`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val stacks = mutableListOf<ArrayDeque<Char>>()
    val instructions = mutableListOf<Triple<Int, Int, Int>>()

    var parsingStack = true

    for (line in lines) {
        if (parsingStack) {
            // Parsing stack
            if (line.isBlank()) {
                parsingStack = false
                continue
            } else {
                for (i in line.indices step 4) {
                    if (line[i] == '[') {
                        val stackIndex = i / 4
                        while (stacks.size <= stackIndex) {
                            stacks.add(ArrayDeque())
                        }

                        val crate = line[i + 1]
                        stacks[stackIndex].addFirst(crate)
                    }
                }
            }
        } else {
            // Parsing instruction
            val parts = line.split(" ")
            instructions.add(Triple(parts[1].toInt(), parts[3].toInt() - 1, parts[5].toInt() - 1))
        }
    }

    for (instruction in instructions) {
        for (i in 1..instruction.first) {
            stacks[instruction.third].addLast(stacks[instruction.second].removeLast())
        }
    }

    println(stacks.map { it.last() }.joinToString(""))
}