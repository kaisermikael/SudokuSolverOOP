import java.util.*

fun main(args: Array<String>) {
    println("Welcome to the SudokuSolver!" +
            "\nPlease enter the board (found under 'SamplePuzzles/Input/') that you would like solved: ")
    println("(Example Board Inputs: Puzzle-4x4-0001 , Puzzle-9x9-0101 , Puzzle-16x16-0201 , Puzzle-25x25-0901 , Puzzle-36x36-01-A001):")
    val boardInput = readLine()!!
    println("Please enter a name for the output file: ")
    val outputFileName = readLine()!!

    val sudokuSolver = SudokuSolver(boardInput, outputFileName)
    sudokuSolver.runSolver()
}