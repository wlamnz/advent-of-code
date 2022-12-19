package `19`

import kotlin.math.max

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val bluePrints = mutableListOf<BluePrint>()

    for (line in lines) {
        val parts = line.substring(line.indexOf(":") + 1).trim().split(" ")
        val bluePrint = BluePrint(parts[4].toInt(), parts[10].toInt(), Pair(parts[16].toInt(), parts[19].toInt()), Pair(parts[25].toInt(), parts[28].toInt()))
        bluePrints.add(bluePrint)
    }

    var qualityLevel = 0
    bluePrints.forEachIndexed { index, bp ->
        val maxConsumableOre = max(max(max(bp.oreRobotCost, bp.clayRobotCost), bp.obsidianRobotCost.first), bp.geodeRobotCost.first)
        qualityLevel += (index + 1) * solve(bp, 24, 0, 0, 0, 0, 1, 0, 0, 0, maxConsumableOre, mutableMapOf())
    }
    println(qualityLevel)
}

private fun solve(bp: BluePrint,
                  mins: Int,
                  ores: Int,
                  clay: Int,
                  obsidian: Int,
                  geode: Int,
                  oreCollectingRobot: Int,
                  clayCollectingRobot: Int,
                  obsidianCollectingRobot: Int,
                  geodeCollectingRobot: Int,
                  maxConsumableOre: Int,
                  dp: MutableMap<Long, Int>): Int {
    if (mins == 0) {
        return geode
    }

    val state = hash(mins, ores, clay, obsidian, oreCollectingRobot, clayCollectingRobot, obsidianCollectingRobot, geodeCollectingRobot)

    if (dp.contains(state)) {
        return dp[state]!!
    }

    var best = 0
    var canBuildCount = 0

    if (ores >= bp.oreRobotCost && oreCollectingRobot < maxConsumableOre) {
        // Enough to build ore robot
        canBuildCount++
        best = max(best, solve(bp, mins - 1, ores - bp.oreRobotCost + oreCollectingRobot, clay + clayCollectingRobot, obsidian + obsidianCollectingRobot, geode + geodeCollectingRobot, oreCollectingRobot + 1, clayCollectingRobot, obsidianCollectingRobot, geodeCollectingRobot, maxConsumableOre, dp))
    }

    if (ores >= bp.clayRobotCost && clayCollectingRobot < bp.obsidianRobotCost.second) {
        // Enough to build clay robot
        canBuildCount++
        best = max(best, solve(bp, mins - 1, ores - bp.clayRobotCost + oreCollectingRobot, clay + clayCollectingRobot, obsidian + obsidianCollectingRobot, geode + geodeCollectingRobot, oreCollectingRobot, clayCollectingRobot + 1, obsidianCollectingRobot, geodeCollectingRobot, maxConsumableOre, dp))
    }

    if (ores >= bp.obsidianRobotCost.first && clay >= bp.obsidianRobotCost.second && obsidianCollectingRobot < bp.geodeRobotCost.second) {
        // Enough to build obsidian robot
        canBuildCount++
        best = max(best, solve(bp, mins - 1, ores - bp.obsidianRobotCost.first + oreCollectingRobot, clay - bp.obsidianRobotCost.second + clayCollectingRobot, obsidian + obsidianCollectingRobot, geode + geodeCollectingRobot, oreCollectingRobot, clayCollectingRobot, obsidianCollectingRobot + 1, geodeCollectingRobot, maxConsumableOre, dp))
    }

    if (ores >= bp.geodeRobotCost.first && obsidian >= bp.geodeRobotCost.second) {
        // Enough to build geode robot
        canBuildCount++
        best = max(best, solve(bp, mins - 1, ores - bp.geodeRobotCost.first + oreCollectingRobot, clay + clayCollectingRobot, obsidian - bp.geodeRobotCost.second + obsidianCollectingRobot, geode + geodeCollectingRobot, oreCollectingRobot, clayCollectingRobot, obsidianCollectingRobot, geodeCollectingRobot + 1, maxConsumableOre, dp))
    }

    // If we can build any of the robot, then it doesn't make sense to not build any.
    if (canBuildCount != 4) {
        // Don't build any robots
        best = max(best, solve(bp, mins - 1, ores + oreCollectingRobot, clay + clayCollectingRobot, obsidian + obsidianCollectingRobot, geode + geodeCollectingRobot, oreCollectingRobot, clayCollectingRobot, obsidianCollectingRobot, geodeCollectingRobot, maxConsumableOre, dp))
    }

    dp[state] = best
    return best
}

private fun hash(mins: Int, ores: Int, clay: Int, obsidian: Int, oreCollectingRobot: Int, clayCollectingRobot: Int, obsidianCollectingRobot: Int, geodeCollectingRobot: Int): Long {
    val sb = StringBuilder()

    sb.append(mins + 10)
    sb.append(ores + 10)
    sb.append(clay + 10)
    sb.append(obsidian + 10)
    sb.append(oreCollectingRobot + 10)
    sb.append(clayCollectingRobot + 10)
    sb.append(obsidianCollectingRobot + 10)
    sb.append(geodeCollectingRobot + 10)

    return sb.toString().toLong()
}