package `10`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val q = ArrayDeque(lines)
    var valueToBeAdded = 0
    var instructionCycleRemaining = 0
    var x = 1
    var cycles = 0
    var signalCheck = 20
    var strength = 0

    while (cycles < 220) {
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

        cycles++
        if (signalCheck == cycles) {
            strength += signalCheck * x
            signalCheck += 40
        }
    }

    println(strength)
}