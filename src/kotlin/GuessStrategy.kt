import com.google.gson.*

class GuessStrategy: CellSolutionStrategy() {
    override fun findApplicableCells(board: Board): Pair<List<Cell>, Any> {
        val applicableCells = mutableListOf<Cell>()
        for (cell in board.cells.cells) {
            if (!cell.complete) {
                applicableCells.add(cell)
            }
        }
        return Pair(applicableCells.toList(), "Guess")
    }

    override fun applyChanges(board: Board, cells: List<Cell>, manipulationParam: Any): Boolean {
        for (cell in board.cells.cells) {
            if (!cell.complete) {
                for (possibleVal in cell.possibleVals) {
                    val cloneBoard = copyBoard(board)
                    val cellToChange = cloneBoard.getCellByCoord(cell.coord.first, cell.coord.second)
                    cellToChange.setCellCurrentVal(possibleVal)
                    BacktrackStack.stack.add(cloneBoard)
                    cell.markAsComplete()
                }
            }
        }
        this.stopTimer()
        AlgorithmStats.updateAlgorithm("GuessStrategy", this.elapsedTime)
        return true
    }

    private fun copyBoard(board: Board): Board {
        val stringBoard = Gson().toJson(board, Board::class.java)
        return Gson().fromJson(stringBoard, Board::class.java)
    }

}