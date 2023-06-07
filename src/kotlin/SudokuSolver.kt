import java.io.FileOutputStream
import java.io.PrintStream

class SudokuSolver(boardFilePath: String, boardOutputPath: String) {
    private var boardFilePath = ""
    private val out = PrintStream(FileOutputStream("$boardOutputPath.txt"))

    init {
        this.boardFilePath = boardFilePath
    }

    fun runSolver() {
        System.setOut(out)
        val fullFilePath = "src/main/resources/SamplePuzzles/Input/$boardFilePath.txt"
        val readBoard = BoardFileReader.processFile(fullFilePath)

        val boardFactory = BoardFactory(readBoard)

        val originalBoard = boardFactory.createBoard()

        if (originalBoard.boardStatus != "") {
            originalBoard.printFullBoard()
            println(originalBoard.boardStatus)
            return
        }

        originalBoard.printFullBoard()
        println()

        // TESTING FUNCTIONS
//        originalBoard.printByRows()
//        originalBoard.printByColumns()
//        originalBoard.printByBlocks()

        val currentBoard = boardFactory.createBoard()
        currentBoard.updatePossibleValues()
        val runtimeResults = algoLooper(currentBoard)
        if (runtimeResults.second > 1) {
            println("Invalid Board: Multiple Solutions Found!")
        } else if (runtimeResults.second == 1) {
            printResults(currentBoard)
        } else {
            println("Invalid Board: No Solution Found!")
        }
    }

    private fun algoLooper(inputBoard: Board): Pair<Board, Int> {
        var madeChange = false
        var currentAlgorithm = 0
        var solutionCount = 0
        var currentBoard = inputBoard

        val possibleAlgorithms = mapOf<String, CellSolutionStrategy>(
            Pair("NakedSingleStrategy", NakedSingleStrategy()),
            Pair("UniqueCandidateStrategy", UniqueCandidateStrategy()),
            Pair("RowPairStrategy", RowPairStrategy()),
            Pair("ColumnPairStrategy", ColumnPairStrategy()),
            Pair("GuessStrategy", GuessStrategy()),
        )

        for (algorithm in possibleAlgorithms) {
            AlgorithmStats.addAlgorithm(algorithm.key)
        }

        while(!currentBoard.checkIfComplete()) {
            when (currentAlgorithm) {
                0 -> {
                    madeChange = possibleAlgorithms["NakedSingleStrategy"]!!.execute(currentBoard)
                }
                1 -> {
                    madeChange = possibleAlgorithms["UniqueCandidateStrategy"]!!.execute(currentBoard)
                }
                2 -> {
                    madeChange = possibleAlgorithms["RowPairStrategy"]!!.execute(currentBoard)
                }
                3 -> {
                    madeChange = possibleAlgorithms["ColumnPairStrategy"]!!.execute(currentBoard)
                }
                4 -> {
                    madeChange = possibleAlgorithms["GuessStrategy"]!!.execute(currentBoard)
                }
            }


            if (madeChange && !currentBoard.checkIfComplete()) {
                madeChange = false
                currentAlgorithm = 0
                currentBoard.updatePossibleValues()
                println()
            } else {
                currentAlgorithm++
                if (!madeChange && currentAlgorithm == possibleAlgorithms.size) {
                    if (BacktrackStack.stack.isEmpty() && solutionCount == 0) {
                        break
                    } else {
                        currentBoard = BacktrackStack.stack.removeLast()
                    }
                } else {
                    if (currentBoard.checkIfComplete()) {
                        solutionCount++
                        if (solutionCount > 1 || BacktrackStack.stack.isEmpty()) {
                            break
                        } else {
                            currentBoard = BacktrackStack.stack.removeLast()
                        }
                    }
                }
            }
        }
        return Pair(currentBoard, solutionCount)
    }

    private fun printResults(board: Board) {
        println("\nSolution:")
        println()
        board.printBoard()
        println("\nTotal Time: " + AlgorithmStats.getTotalTime())
        println()
        println("Strategy                 Uses Time")
        println("-------------------------------------")
        for (algorithm in AlgorithmStats.algorithms) {
            algorithm.printAlgoStats()
        }
    }
}