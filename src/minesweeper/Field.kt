package minesweeper

import kotlin.random.Random

class Field(val size: Int = 9, val mines: Int = 10) {
    private val last = size - 1
    private var countOfMine = 0

    private val cells = Array(size) { Array(size) { Cell(CellValue.EMPTY) } }

    init {
        while (countOfMine < mines) {
            val row = Random.nextInt(size)
            val column = Random.nextInt(size)
            if (!addMine(row, column)) continue
            increaseCounterforEachNeighbor(row, column)
            countOfMine++
        }
    }

    fun existsCell(row: Int, column: Int): Boolean {
        return row in 0..last
                && column in 0..last
    }

    fun isOpenCell(row: Int, column: Int): Boolean {
        return cells[row][column].isOpen()
    }

    private fun addMine(row: Int, column: Int): Boolean {
        if (!existsCell(row, column)) return false
        if (cells[row][column].isMine()) return false

        cells[row][column].value = CellValue.MINE
        return true
    }

    private fun increaseCounterforEachNeighbor(row: Int, column: Int) {
        val neighbors = arrayOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))

        neighbors.forEach {
            val neighborRow = row + it.first
            val neighborColumn = column + it.second

            if (existsCell(neighborRow, neighborColumn) &&
                    !cells[neighborRow][neighborColumn].isMine()) {
                cells[neighborRow][neighborColumn].value = CellValue.MINE
            }
        }
    }
}
/*    fun allToString(): String {
        val printField = StringBuilder()
        cells.forEach {
            it.joinTo(printField, "") {
                when (it) {
                    0 -> BLANK
                    MINE_NUM -> MINE
                    else -> it.toString()
                }
            }
            printField.appendln()
        }
        return printField.toString()
    }
*/
