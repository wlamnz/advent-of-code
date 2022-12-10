package `10`


fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val q = ArrayDeque(lines)
    var valueToBeAdded = 0
    var instructionCycleRemaining = 0
    var x = 1
    var cycles = 0
    var signalCheck = 40
    val sb = StringBuilder()

    while (cycles < 240) {
        if (instructionCycleRemaining != 0) {
            instructionCycleRemaining--
        } else {
            x += valueToBeAdded
            valueToBeAdded = 0

            if (!q.isEmpty()) {
                val parts = q.removeFirst().split(" ")

                if (parts[0] == "addx") {
                    valueToBeAdded = parts[1].toInt()
                    instructionCycleRemaining = 1
                }
            }
        }

        if (sb.length >= x - 1 && sb.length <= x + 1) {
            sb.append("#")
        } else {
            sb.append(".")
        }

        cycles++
        if (signalCheck == cycles) {
            signalCheck += 40
            println(sb)
            sb.clear()
        }
    }
}