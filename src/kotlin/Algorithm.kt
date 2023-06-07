import java.util.concurrent.TimeUnit

class Algorithm(name: String) {
    val name: String
    var useCount: Int = 0
    var useTime: Long = 0

    init {
        this.name = name
    }

    fun printAlgoStats() {
        var formatName = name
        val missingSpace = 25 - name.length
        for (i in 0 until missingSpace) {
            formatName += " "
        }
        var formatUseCount = useCount.toString()
        val missingSpace2 = 5 - useCount.toString().length
        for (i in 0 until missingSpace2) {
            formatUseCount += " "
        }
        var minutes = TimeUnit.MILLISECONDS.toMinutes(useTime.toInt().toLong())
        var seconds = TimeUnit.MILLISECONDS.toSeconds(useTime.toInt().toLong()) - TimeUnit.MINUTES.toSeconds(minutes)
        var milliseconds = TimeUnit.MILLISECONDS.toMillis(useTime.toInt().toLong()) - TimeUnit.SECONDS.toMillis(seconds)
        var time = String.format("%02d:%02d.%03d", minutes, seconds, milliseconds)
        println("$formatName$formatUseCount$time")
    }
}