abstract class CellSolutionStrategy {
    var numUses = 0
    var elapsedTime = 0L
    var startTime = 0L

    fun execute(board: Board): Boolean {
        startTimer()
        board.updatePossibleValues()
        val result = findApplicableCells(board)
        val cells = result.first
        var changeMade = false
        val manipulationParam = result.second
        if (cells.isNotEmpty()) {
            changeMade = applyChanges(board, cells, manipulationParam)
            if (changeMade) { numUses++ }
            board.updatePossibleValues()
        }
        return changeMade
    }

    abstract fun findApplicableCells(board: Board): Pair<List<Cell>, Any>
    abstract fun applyChanges(board: Board, cells: List<Cell>, manipulationParam: Any): Boolean

    private fun startTimer() {
        this.startTime = System.currentTimeMillis()
    }

    protected fun stopTimer() {
        val endTime = System.currentTimeMillis()
        elapsedTime += (endTime - startTime)
    }
}