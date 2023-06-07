import java.io.File

object BoardFileReader {

    fun processFile(filePath: String): List<List<String>> {
        val file = File(filePath)
        val lines = file.readLines()
        val boardInfo = mutableListOf<List<String>>()
        for (i in lines.indices) {
            if (i < 2) {
                boardInfo.add(listOf(lines[i]))
                continue
            } else {
                boardInfo.add(lines[i].split(" "))
            }
        }
        return boardInfo.toList()
    }
}