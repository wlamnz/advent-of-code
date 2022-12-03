package `3`

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input").bufferedReader().readLines()

    var sum = 0

    for (i in lines.indices step 3) {
        val all = mutableSetOf<Char>()
        val r1 = lines[i]
        val r2 = lines[i + 1]
        val r3 = lines[i + 2]

        r1.forEach { all.add(it) }
        r2.forEach { all.add(it) }
        r3.forEach { all.add(it) }

        for (c in all) {
            if (r1.contains(c) && r2.contains(c) && r3.contains(c)) {
                sum += if (c in 'a'..'z') {
                    c - 'a' + 1
                } else {
                    c - 'A' + 27
                }
            }
        }
    }

    println(sum)
}