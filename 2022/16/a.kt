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

    println(solve(30, "AA", valvesById, setOf(), mutableMapOf()))
}

private fun solve(mins: Int, curValveId: String, valvesById: Map<String, Pair<Int, List<String>>>, opened: Set<String>, dp: MutableMap<String, Int>): Int {
    if (mins == 0) {
        return 0
    }

    val sb = StringBuilder()
    sb.append(mins)
    sb.append(curValveId)
    sb.append(opened.sorted())
    val state = sb.toString()

    if (dp[state] != null) {
        return dp[state]!!
    }

    val curValve = valvesById[curValveId]
    var best = 0

    if (curValve != null) {
        if (curValve.first > 0 && !opened.contains(curValveId)) {
            val newOpened = mutableSetOf<String>()
            newOpened.addAll(opened)
            newOpened.add(curValveId)

            best = max(best, ((mins - 1) * curValve.first) + solve(mins - 1, curValveId, valvesById, newOpened, dp))
        }

        for (next in curValve.second) {
            best = max(best, solve(mins - 1, next, valvesById, opened, dp))
        }
    }

    dp[state] = best
    return best
}