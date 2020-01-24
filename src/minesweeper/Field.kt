package minesweeper

import kotlin.random.Random

class Field(val size: Int = 9, val mines: Int = 10) {
    private val last = size - 1
    private var countOfMine = 0

    val cells = Array(size) { Array(size) { Cell(false) } }

    init {
        createMines()
        increaseCountersOfMines()
    }

    private fun increaseCountersOfMines() {
        for (i in 0..last){
            for (j in 0..last) {
                if (cells[i][j].mine) {
                    increaseCounterforEachNeighbor(i,j)
                }
            }
        }
    }

    private fun createMines() {
        while (countOfMine < mines) {
            val row = Random.nextInt(size)
            val column = Random.nextInt(size)
            if (!addMine(row, column)) continue
            countOfMine++
        }
    }


    fun existsCell(row: Int, column: Int): Boolean {
        return row in 0..last
                && column in 0..last
    }

    fun getCell(row: Int, column: Int): Cell? {
        if (!existsCell(row, column)) return null
        return cells[row][column]
    }

    fun isOpenCell(row: Int, column: Int): Boolean {
        return cells[row][column].open
    }

    private fun addMine(row: Int, column: Int): Boolean {
        if (!existsCell(row, column)) return false
        if (cells[row][column].mine) return false

        cells[row][column].mine = true
        return true
    }

    private fun increaseCounterforEachNeighbor(row: Int, column: Int) {
        val neighbors = arrayOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))

        neighbors.forEach {
            val neighborRow = row + it.first
            val neighborColumn = column + it.second

            if (existsCell(neighborRow, neighborColumn)) {
                val neighbor = cells[neighborRow][neighborColumn]
                if (!neighbor.mine) neighbor.cntOfmineInNeighbors++
            }
        }
    }

    fun openedToString(): String {
        val printField = StringBuilder()
        cells.forEach {
            it.joinTo(printField, "") { cell ->
                cell.stateToString()
            }
            printField.appendln()
        }
        return printField.toString()
    }
}

private fun Cell.stateToString(): String {
    if (open) {
        return when {
            mine -> "X"
            cntOfmineInNeighbors > 0 -> cntOfmineInNeighbors.toString()
            else -> "/" //cntOfMineInNeighbors == 0
        }
    }
    // if close
    return when {
        mineFlag -> "*"
        else -> "."
    }
}