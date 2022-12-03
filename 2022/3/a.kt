package `3`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()

    var sum = 0

    for (line in lines) {
        val c1 = line.substring(0, line.length / 2)
        val c2 = line.substring(line.length / 2)

        for (c in c1) {
            if (c2.contains(c)) {
                sum += if (c in 'a'..'z') {
                    c - 'a' + 1
                } else {
                    c - 'A' + 27
                }
                break
            }
        }
    }

    println(sum)
}