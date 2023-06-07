class ColumnPairStrategy: CellSolutionStrategy() {
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
                    val possibleBlockColumns = ArrayList<Column>()
                    for (cell in possibleBlocksCells) {
                        val possibleColumn = board.findColumnByCell(cell)
                        if (!possibleBlockColumns.contains(possibleColumn)) {
                            possibleBlockColumns.add(possibleColumn)
                        }
                    }
                    if (possibleBlockColumns.size > 1) {
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
        return Pair(applicableCells.toList(), "ColumnPairStrategy")
    }

    override fun applyChanges(board: Board, cells: List<Cell>, manipulationParam: Any): Boolean {
        val changedColumns = ArrayList<Column>()
        var madeChange = false
        for (cell in cells) {
            val currentColumn = board.findColumnByCell(cell)
            if (!changedColumns.contains(currentColumn)) {
                val safeBlock = board.findBlockByCell(cell)
                changedColumns.add(currentColumn)
                for (columnCell in currentColumn.cells) {
                    for (blockPairVal in cell.blockPairVal) {
                        if (columnCell.possibleVals.contains(blockPairVal) && !safeBlock.cells.contains(columnCell)) {
                            columnCell.possibleVals.remove(blockPairVal)
                            madeChange = true
                        }
                    }

                }
            }
        }
        this.stopTimer()
        AlgorithmStats.updateAlgorithm("ColumnPairStrategy", this.elapsedTime)
        return madeChange
    }

}
