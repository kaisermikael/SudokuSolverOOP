class NakedSingleStrategy: CellSolutionStrategy() {
    override fun findApplicableCells(board: Board): Pair<List<Cell>, Any> {
        val applicableCells = mutableListOf<Cell>()
        for (cell in board.cells.cells) {
            if (cell.currentVal == "-" && cell.possibleVals.size == 1 && !cell.complete) {
                applicableCells.add(cell)
            }
        }
        return Pair(applicableCells.toList(), "NakedSingles")
    }

    override fun applyChanges(board: Board, cells: List<Cell>, manipulationParam: Any): Boolean {
        for (cell in cells) {
            cell.markAsComplete()
            cell.setCellCurrentVal(cell.possibleVals.first())
            cell.possibleVals.clear()
        }
        this.stopTimer()
        AlgorithmStats.updateAlgorithm("NakedSingleStrategy", this.elapsedTime)
        return true
    }

}