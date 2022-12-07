package `7`

class File(val name: String, val size: Long)

class Directory(val name: String, val upperDirectory: Directory?) {
    val directories = mutableMapOf<String, Directory>()
    val files = mutableListOf<File>()

    fun getSize(): Long {
        return directories.values.sumOf { it.getSize() } + files.sumOf { it.size }
    }
}