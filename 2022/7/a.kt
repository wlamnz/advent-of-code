package `7`

import java.util.Stack

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()
    val root = Directory("/", null)
    var curDir: Directory? = root

    for (line in lines) {
        if (curDir == null) {
            throw IllegalStateException("Cur dir is null")
        }
        val parts = line.split(" ")

        if (line.startsWith("$")) {
            if (parts[1] == "cd") {
                val destDir = parts[2]
                curDir = when (destDir) {
                    "/" -> root
                    ".." -> curDir.upperDirectory
                    else -> curDir.directories[destDir]
                }
            } else if (parts[1] == "ls") {
                continue
            }
        } else {
            if (parts[0] == "dir") {
                val dirName = parts[1]
                if (!curDir.directories.containsKey(dirName)) {
                    curDir.directories[dirName] = Directory(dirName, curDir)
                }
            } else {
                val fileName = parts[1]
                curDir.files.add(File(fileName, parts[0].toLong()))
            }
        }
    }

    val stack = Stack<Directory>()
    stack.push(root)

    var totalSize : Long = 0

    while (!stack.isEmpty()) {
        val dir = stack.pop()
        val size = dir.getSize()
        if (size <= 100000) {
            totalSize += size
        }

        dir.directories.values.forEach { stack.push(it) }
    }

    println(totalSize)
}