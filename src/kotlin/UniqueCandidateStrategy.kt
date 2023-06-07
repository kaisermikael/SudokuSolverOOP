class UniqueCandidateStrategy: CellSolutionStrategy() {
    override fun findApplicableCells(board: Board): Pair<List<Cell>, Any> {
        val applicableCells = mutableListOf<Cell>()
        val uniqueCandidate = mutableListOf<String>()
        val possibleCells = mutableListOf<Cell>()

        for (symbol in board.possibleSymbols) {
            for (row in board.rows) {
                var symbolCount = 0
                for (cell in row.cells) {
                    if (cell.possibleVals.contains(symbol)) {
                        symbolCount++
                        possibleCells.add(cell)
                    }
                }
                if (symbolCount == 1) {
                    if (!applicableCells.contains(possibleCells[0])) {
                        applicableCells.add(possibleCells[0])
                        uniqueCandidate.add(symbol)
                    }
                }
                possibleCells.clear()
            }
            for (column in board.columns) {
                var symbolCount = 0
                for (cell in column.cells) {
                    if (cell.possibleVals.contains(symbol)) {
                        symbolCount++
                        possibleCells.add(cell)
                    }
                }
                if (symbolCount == 1) {
                    if (!applicableCells.contains(possibleCells[0])) {
                        applicableCells.add(possibleCells[0])
                        uniqueCandidate.add(symbol)
                    }
                }
                possibleCells.clear()
            }
            for (block in board.blocks) {
                var symbolCount = 0
                for (cell in block.cells) {
                    if (cell.possibleVals.contains(symbol)) {
                        symbolCount++
                        possibleCells.add(cell)
                    }
                }
                if (symbolCount == 1) {
                    if (!applicableCells.contains(possibleCells[0])) {
                        applicableCells.add(possibleCells[0])
                        uniqueCandidate.add(symbol)
                    }
                }
                possibleCells.clear()
            }
        }
        return Pair(applicableCells.toList(), uniqueCandidate)
    }

    override fun applyChanges(board: Board, cells: List<Cell>, manipulationParam: Any): Boolean {
        if (manipulationParam is List<*>) {
            for (i in cells.indices) {
                cells[i].markAsComplete()
                cells[i].setCellCurrentVal(manipulationParam[i] as String)
                cells[i].possibleVals.clear()
            }
            this.stopTimer()
            AlgorithmStats.updateAlgorithm("UniqueCandidateStrategy", this.elapsedTime)
            return true
        }
        return false
    }

}
