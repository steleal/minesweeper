package minesweeper

import kotlin.random.Random

class Field(val size: Int = 9, val mines: Int = 10) {
    private val last = size - 1
    private var countOfMine = 0

    private val BLANK = "." //for printing
    private val MINE = "X" //for printing
    private val MINE_NUM = -1  //for keeping in array of cells

    private val cells = Array(size) { IntArray(size) { 0 } }

    init {
        while (countOfMine < mines) {
            val row = Random.nextInt(size)
            val column = Random.nextInt(size)
            addMine(row, column)
        }
    }

    fun allToString(): String {
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

    private fun addMine(row: Int, column: Int): Boolean {
        if (!existCells(row, column)) return false
        if (isMine(row, column)) return false

        cells[row][column] = MINE_NUM
        countOfMine++
        increaseCounterforEachNeighbor(row, column)
        return true
    }

    private fun isMine(row: Int, column: Int): Boolean {
        return cells[row][column] == MINE_NUM
    }

    private fun existCells(row: Int, column: Int): Boolean {
        return row in 0..last
                && column in 0..last
    }

    private fun increaseCounterforEachNeighbor(row: Int, column: Int) {
        val neighbors = arrayOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))

        neighbors.forEach {
            val neighborRow = row + it.first
            val neighborColumn = column + it.second
            if (existCells(neighborRow, neighborColumn) &&
                    cells[neighborRow][neighborColumn] != MINE_NUM) {

                cells[neighborRow][neighborColumn]++
            }
        }
    }

}
