import java.util.concurrent.TimeUnit

object AlgorithmStats {
    var algorithms = mutableListOf<Algorithm>()
        private set

    fun getTotalTime(): String {
        var totalTime = 0.0
        for (algorithm in algorithms) {
            totalTime += algorithm.useTime
        }

        var minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime.toInt().toLong())
        var seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime.toInt().toLong()) - TimeUnit.MINUTES.toSeconds(minutes)
        var milliseconds = TimeUnit.MILLISECONDS.toMillis(totalTime.toInt().toLong()) - TimeUnit.SECONDS.toMillis(seconds)
        return String.format("%02d:%02d.%03d", minutes, seconds, milliseconds)
    }

    fun addAlgorithm(algorithmName: String) {
        algorithms.add(Algorithm(algorithmName))
    }

    fun updateAlgorithm(algorithmName: String, time: Long) {
        algorithms.find { it.name == algorithmName }?.let {
            it.useCount++
            it.useTime += time
        }
    }
}