package minesweeper

import kotlin.random.Random

class Field(
        val size: Int = 9,
        val mines: Int = 10
) {

    val cells = Array(size) { i ->
        Array(size) { j ->
            Cell(i, j)
        }
    }
    private var countOfMine = 0

    init {
        createMines()
        calculateMineCounters()
    }

    fun getCell(row: Int, column: Int): Cell? {
        if (!existsCell(row, column)) return null
        return cells[row][column]
    }

    fun getNeighbors(cell: Cell): List<Cell> {
        val deviations = arrayOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))

        val neighbors = mutableListOf<Cell>()

        deviations.forEach {
            val row = cell.row + it.first
            val column = cell.column + it.second
            if (existsCell(row, column)) {
                neighbors.add(cells[row][column])
            }
        }

        return neighbors
    }

    fun recalculateMineCounters() {
        resetMineCounters()
        calculateMineCounters()
    }

    private fun createMines() {
        while (countOfMine < mines) {
            val row = Random.nextInt(size)
            val column = Random.nextInt(size)
            if (!addMine(row, column)) continue
            countOfMine++
        }
    }

    private fun resetMineCounters() {
        forEachCell { it.cntOfmineInNeighbors = 0 }
    }

    private fun calculateMineCounters() {
        forEachCell { cell ->
            if (cell.mine) {
                increaseCounterforEachNeighbor(cell)
            }
        }
    }

    private fun forEachCell(action: (Cell) -> Unit) {
        cells.forEach { line ->
            line.forEach { cell ->
                cell.apply(action)
            }
        }
    }

    private fun addMine(row: Int, column: Int): Boolean {
        if (!existsCell(row, column)) return false
        if (cells[row][column].mine) return false

        cells[row][column].mine = true
        return true
    }

    private fun increaseCounterforEachNeighbor(cell: Cell) {
        val neighbors = getNeighbors(cell)

        neighbors.forEach { neighbor ->
            if (!neighbor.mine) neighbor.cntOfmineInNeighbors++
        }
    }

    private fun existsCell(row: Int, column: Int): Boolean {
        val last = size - 1
        return row in 0..last
                && column in 0..last
    }

}
