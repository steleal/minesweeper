package minesweeper

import kotlin.random.Random

class Field(val size: Int = 9, val mines: Int = 10) {
    private val last = size - 1
    private var countOfMine = 0

    val cells = Array(size) { i ->
        Array(size) { j ->
            Cell(i, j)
        }
    }

    init {
        createMines()
        calculateMineCounters()
    }

    fun calculateMineCounters() {
        forEachCell { it.cntOfmineInNeighbors = 0 }

        forEachCell { cell ->
            if (cell.mine) {
                increaseCounterforEachNeighbor(cell)
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

    fun forEachCell(action: (Cell) -> Unit) {
        cells.forEach { line ->
            line.forEach { cell ->
                cell.apply(action)
            }
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

    private fun increaseCounterforEachNeighbor(cell: Cell) {
        val neighbors = getNeighbors(cell)

        neighbors.forEach { neighbor ->
            if (!neighbor.mine) neighbor.cntOfmineInNeighbors++
        }
    }

    fun getNeighbors(cell: Cell): List<Cell> {
        val neighbors = arrayOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))
        val list = mutableListOf<Cell>()
        neighbors.forEach {
            val neighborRow = cell.i + it.first
            val neighborColumn = cell.j + it.second
            if (existsCell(neighborRow, neighborColumn)) {
                list.add(cells[neighborRow][neighborColumn])
            }
        }
        return list
    }

    fun openedToString(): String {
        val printField = StringBuilder()
        printField.appendHeader(size)
        printField.appendDelimiter(size)
        for (i in 0..last) {
            printField.appendRow(cells[i], i)
        }
        printField.appendDelimiter(size)
        return printField.toString()
    }
}

private fun java.lang.StringBuilder.appendRow(cells: Array<Cell>, i: Int) {
    this.append((i + 1) % 10)
    this.append("│")
    cells.joinTo(this, "") { cell ->
        cell.stateToString()
    }
    this.appendln("│")
}

private fun java.lang.StringBuilder.appendHeader(columns: Int) {
    this.append(" │")
    for (i in 1..columns) {
        this.append(i % 10)
    }
    this.appendln("│")
}

private fun java.lang.StringBuilder.appendDelimiter(columns: Int) {
    this.append("—│")
    repeat(columns) {
        this.append("—")
    }
    this.appendln("│")
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
