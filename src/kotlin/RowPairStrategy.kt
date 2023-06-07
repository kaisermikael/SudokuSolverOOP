class RowPairStrategy: CellSolutionStrategy() {
    override fun findApplicableCells(board: Board): Pair<List<Cell>, Any> {
        val applicableCells = mutableListOf<Cell>()

        for (symbol in board.possibleSymbols) {
            for (block in board.blocks) {
                val possibleBlocksCells = ArrayList<Cell>()
                for (cell in block.cells) {
                    if (cell.possibleVals.contains(symbol)) {
                        possibleBlocksCells.add(cell)
                    }
                }
                if (possibleBlocksCells.size > 1) {
                    val possibleBlockRows = ArrayList<Row>()
                    for (cell in possibleBlocksCells) {
                        val possibleRow = board.findRowByCell(cell)
                        if (!possibleBlockRows.contains(possibleRow)) {
                            possibleBlockRows.add(possibleRow)
                        }
                    }
                    if (possibleBlockRows.size > 1) {
                        continue
                    } else {
                        applicableCells.addAll(possibleBlocksCells)
                        possibleBlocksCells.forEach {
                            it.blockPairVal.add(symbol)
                        }
                    }
                }
            }

        }
        return Pair(applicableCells.toList(), "RowPairStrategy")
    }

    override fun applyChanges(board: Board, cells: List<Cell>, manipulationParam: Any): Boolean {
        val changedRows = ArrayList<Row>()
        var madeChange = false
        for (cell in cells) {
            val currentRow = board.findRowByCell(cell)
            if (!changedRows.contains(currentRow)) {
                val safeBlock = board.findBlockByCell(cell)
                changedRows.add(currentRow)
                for (rowCell in currentRow.cells) {
                    for (blockPairVal in cell.blockPairVal) {
                        if (rowCell.possibleVals.contains(blockPairVal) && !safeBlock.cells.contains(rowCell)) {
                            rowCell.possibleVals.remove(blockPairVal)
                            madeChange = true
                        }
                    }

                }
            }
        }
        this.stopTimer()
        AlgorithmStats.updateAlgorithm("RowPairStrategy", this.elapsedTime)
        return madeChange
    }

}
