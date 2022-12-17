package `16`

import kotlin.math.max

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val valvesById = mutableMapOf<String, Pair<Int, List<String>>>()

    for (line in lines) {
        val parts = line.split(";")
        val id = parts[0].split(" ")[1]
        val flowRate = parts[0].split("=").last().toInt()
        val tunnels = parts[1].trim().substring("tunnels lead to valves".length).trim().split(", ").toList()

        valvesById[id] = Pair(flowRate, tunnels)
    }

    println(solve(26, "AA", "AA", valvesById, setOf(), mutableMapOf()))
}

private fun solve(mins: Int, curValveId: String, eleValveId: String, valvesById: Map<String, Pair<Int, List<String>>>, opened: Set<String>, dp: MutableMap<String, Int>): Int {
    if (mins == 0) {
        return 0
    }

    // Elephant follows the same rule as me. Doesn't matter if our positions are swapped.
    val sortedValvePositions = listOf(curValveId, eleValveId).sorted().toString()

    val sb = StringBuilder()
    sb.append(mins)
    sb.append(sortedValvePositions)
    sb.append(opened.sorted())
    val state = sb.toString()

    if (dp[state] != null) {
        return dp[state]!!
    }

    val curValve = valvesById[curValveId]
    val eleValve = valvesById[eleValveId]
    var best = 0

    if (curValve != null && eleValve != null) {
        if (curValveId != eleValveId) {
            // I open, ele moves
            if (curValve.first > 0 && !opened.contains(curValveId)) {
                val newOpened = mutableSetOf<String>()
                newOpened.addAll(opened)
                newOpened.add(curValveId)

                for (eleNext in eleValve.second) {
                    best = max(best, ((mins - 1) * curValve.first) + solve(mins - 1, curValveId, eleNext, valvesById, newOpened, dp))
                }
            }

            // Ele opens, I move
            if (eleValve.first > 0 && !opened.contains(eleValveId)) {
                val newOpened = mutableSetOf<String>()
                newOpened.addAll(opened)
                newOpened.add(eleValveId)

                for (meNext in curValve.second) {
                    best = max(best, ((mins - 1) * eleValve.first) + solve(mins - 1, meNext, eleValveId, valvesById, newOpened, dp))
                }
            }

            // We both open
            if (curValve.first > 0 && !opened.contains(curValveId) && eleValve.first > 0 && !opened.contains(eleValveId)) {
                val newOpened = mutableSetOf<String>()
                newOpened.addAll(opened)
                newOpened.add(curValveId)
                newOpened.add(eleValveId)

                best = max(best, ((mins - 1) * curValve.first) + ((mins - 1) * eleValve.first) + solve(mins - 1, curValveId, eleValveId, valvesById, newOpened, dp))
            }

        } else {
            if (curValve.first > 0 && !opened.contains(curValveId)) {
                // Doesn't matter who opens. I open, ele moves
                val newOpened = mutableSetOf<String>()
                newOpened.addAll(opened)
                newOpened.add(curValveId)

                for (eleNext in eleValve.second) {
                    best = max(best, ((mins - 1) * curValve.first) + solve(mins - 1, curValveId, eleNext, valvesById, newOpened, dp))
                }
            }
        }

        // Both move without open
        for (meNext in curValve.second) {
            for (eleNext in eleValve.second) {
                best = max(best, solve(mins - 1, meNext, eleNext, valvesById, opened, dp))
            }
        }
    }

    dp[state] = best
    return best
}