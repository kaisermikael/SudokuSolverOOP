import kotlin.math.sqrt

class BoardFactory(cellInput: List<List<String>>): Board() {

    private var cellInput: List<List<String>>

    init {
        this.cellInput = cellInput
    }


    fun createBoard(): Board {
        val board = Board()
        val cells = cellInput.toMutableList()

        val widthInput = cells.removeFirst()
        var possibleSymbolsInput = cells.removeFirst()
        possibleSymbolsInput = possibleSymbolsInput[0].split(" ")

        removeEmptyLines(cells)
        processWidth(widthInput[0].toInt(), board)
        processPossibleSymbols(possibleSymbolsInput, board)


        processCells(cells, board)
        // Only process Cols & Blocks if the board is valid
        if (board.boardStatus != "") {
            return board
        } else {
            processRows(board)
            processColumns(board)
            processBlocks(board)
            return board
        }

    }

    fun processCells(cells: List<List<String>>, board: Board) {
        var status = ""
        val width = board.width

        //Top left -> bottom right
        for (i in cells.indices){
            board.grid.grid.add(ArrayList())
            // Test for uneven number of cells
            if (cells[i].size != width) {
                if (!board.boardStatus.contains("Invalid: Not formatted correctly\n")) {
                    status += "Invalid: Not formatted correctly\n"
                }
            }

            for (j in cells[i].indices) {
                if (cells[i][j] !in "A".."Z" && cells[i][j] !in "a".."z" && cells[i][j] !in "0".."9" && cells[i][j] != "-") {
                    if (!board.boardStatus.contains("Invalid: Invalid cell symbol\n")) {
                        status += "Invalid: Invalid cell symbol\n"
                    }
                }
                val newCell = Cell(cells[i][j], Pair(i, j))
                if (newCell.currentVal == "-") {
                    newCell.setPossibleVals(board.possibleSymbols)
                } else {
                    newCell.markAsComplete()
                }
                board.addCell(newCell)
                board.grid.grid[i].add(newCell)
            }
        }
        board.setBoardStatus(status)
    }

    private fun processRows(board: Board) {
        val width = board.width
        for (i in board.grid.grid.indices) {
            board.rows.add(Row())
            for (j in board.grid.grid[i].indices) {
                board.rows[i].cells.add(board.grid.grid[i][j])
            }
        }
    }

    private fun processColumns(board: Board) {
        val width = board.width
        val height = board.rows.size
        for (i in 0 until width) {
            board.columns.add(Column())
            for (j in 0 until height) {
                board.columns[i].cells.add(board.rows[j].cells[i])
            }
        }
    }

    private fun processBlocks(board: Board) {
        val blockWidth = sqrt(board.width.toDouble()).toInt()
        var totalBlocks = 0
        val grid = board.grid.grid
        // Each block is a square of blockWidth x blockWidth. There are totalBlocks blocks in the grid.
        // The first block is the top left block. Find all blocks and add them to the board.blocks list.
            for (blockRow in 0 until blockWidth) {
                for (blockCol in 0 until blockWidth) {
                    board.blocks.add(Block())
                    totalBlocks++
                    for (j in 0 until blockWidth) {
                        for (k in 0 until blockWidth) {
                            board.blocks[totalBlocks - 1].cells.add(grid[blockRow * blockWidth + j][blockCol * blockWidth + k])
                        }
                    }
                }
            }
    }

    private fun removeEmptyLines(cells: MutableList<List<String>>) {
        for (i in cells.indices) {
            if (cells[i][0] == "") {
                cells.removeAt(i)
            }
        }
    }

    private fun processPossibleSymbols(possibleSymbolsInput: List<String>, board: Board) {
        val possibleSymbols = ArrayList<String>()
        for (i in 0 until possibleSymbolsInput.size) {
            if (possibleSymbolsInput[i] !in "A".."Z" && possibleSymbolsInput[i] !in "a".."z" && possibleSymbolsInput[i] !in "0".."9") {
                if (!board.boardStatus.contains("Invalid: Invalid possible symbol\n")) {
                    board.setBoardStatus("Invalid: Invalid possible symbol\n")
                }
            }
            possibleSymbols.add(possibleSymbolsInput[i])
        }
        board.setBoardPossibleSymbols(possibleSymbols)
    }

    private fun processWidth(widthInput: Int, board: Board) {
        val reasonableSquareWidths = listOf(4, 9, 16, 25, 36, 49, 64, 81, 100)
        if (widthInput !in reasonableSquareWidths) {
            board.setBoardStatus("Invalid: Invalid width\n")
        } else {
            board.setBoardWidth(widthInput)
        }
    }
}