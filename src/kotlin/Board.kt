open class Board() {
    var possibleSymbols = ArrayList<String>()
    var width = 0
        private set
    var boardStatus = ""
        private set

    var cells = AllCells()
    var rows = mutableListOf<Row>()
    var columns = mutableListOf<Column>()
    var blocks = mutableListOf<Block>()
    var grid = Grid()

    fun checkIfComplete():Boolean {
        cells.cells.forEach {
            if (!it.complete) {
                return false
            }
        }
        return true
    }

    fun setBoardWidth(width: Int) {
        this.width = width
    }

    fun setBoardStatus(status: String) {
        this.boardStatus += status
    }

    fun setBoardPossibleSymbols(symbols: ArrayList<String>) {
        this.possibleSymbols = symbols
    }

    fun addCell(newCell: Cell) {
        cells.cells.add(newCell)
    }

    fun findRowByCell(cell: Cell): Row {
        return rows.find { it.cells.contains(cell) }!!
    }

    fun findColumnByCell(cell: Cell): Column {
        return columns.find { it.cells.contains(cell) }!!
    }

    fun findBlockByCell(cell: Cell): Block {
        return blocks.find { it.cells.contains(cell) }!!
    }

    fun updatePossibleValues() {
        val cellIterator = cells.cells.iterator()
        while (cellIterator.hasNext()) {
            val cell = cellIterator.next()
            if (!cell.complete) {
                val currentRow = findRowByCell(cell)
                val currentColumn = findColumnByCell(cell)
                val currentBlock = findBlockByCell(cell)
                val crossValues = mutableListOf<String>()

                currentRow.cells.forEach {
                    if (it.currentVal != "-" && !crossValues.contains(it.currentVal)) {
                        crossValues.add(it.currentVal)
                    }
                }
                currentColumn.cells.forEach {
                    if (it.currentVal != "-" && !crossValues.contains(it.currentVal)) {
                        crossValues.add(it.currentVal)
                    }
                }
                currentBlock.cells.forEach {
                    if (it.currentVal != "-" && !crossValues.contains(it.currentVal)) {
                        crossValues.add(it.currentVal)
                    }
                }

                cell.possibleVals.removeAll(crossValues)
            }
        }
    }

    fun getCellByCoord(x: Int, y: Int): Cell {
        return cells.cells.find { it.coord.first == x && it.coord.second == y }!!
    }

    fun printFullBoard() {
        println(width)
        for (possibleSymbol in possibleSymbols) {
            print("$possibleSymbol ")
        }
        print("\n")
        for (i in grid.grid.indices) {
            for (j in grid.grid[i].indices) {
                print("${grid.grid[i][j].currentVal} ")
            }
            print("\n")
        }
    }

    fun printBoard() {
        for (row in rows) {
            for (cell in row.cells) {
                print(cell.currentVal + " ")
            }
            print("\n")
        }
    }
}