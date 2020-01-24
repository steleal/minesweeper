package minesweeper

import java.util.Scanner

class GameManager(val field: Field, val scanner: Scanner) {
    var over: Boolean = false
    var win: Boolean = false

    fun printField() {
        println(field.openedToString())
    }

    fun nextStep() {
        val (i, j) = askMarkCoordinates()
        val cell = field.getCell(i, j) ?: return
        cell.mineFlag = !cell.mineFlag
        win = checkWinState(field)
        over = win//TODO checkEndOfGame()
    }

    fun openMineNeighbors() {
        field.cells.forEach {
            it.forEach { cell ->
                if (cell.cntOfmineInNeighbors > 0) {
                    cell.open = true
                }
            }
        }
    }

    private fun checkWinState(field: Field): Boolean {
        field.cells.forEach {
            it.forEach { cell ->
                if (cell.mineFlag != cell.mine) return false
            }
        }
        return true
    }

    private fun askMarkCoordinates(): Pair<Int, Int> {
        var i = -1
        var j = -1
        while (!isAcceptable(i, j)) {
            print("Set/delete mines marks (x and y coordinates): ")
            val x = scanner.nextInt()
            val y = scanner.nextInt()
            i = y - 1
            j = x - 1
            printIfOpenedCell(field.getCell(i, j))
        }
        return Pair(i, j)
    }

    private fun printIfOpenedCell(cell: Cell?) {
        cell ?: return
        if (cell.open) {
            when (cell.cntOfmineInNeighbors) {
                0 -> println("Here is a open cell!")
                else -> println("There is a number here!")
            }
        }
    }

    private fun isAcceptable(x: Int, y: Int): Boolean {
        return field.existsCell(x, y) &&
                !field.isOpenCell(x, y)
    }
}
