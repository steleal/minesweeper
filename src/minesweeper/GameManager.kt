package minesweeper

import java.util.Scanner

class GameManager(val field: Field, val scanner: Scanner) {
    var over: Boolean = false
    var win: Boolean = false

    fun printField() {
        println(field.openedToString())
    }

    fun nextStep() {
        val (x, y) = askMarkCoordinates()
        val cell = field.getCell(x, y) ?: return
        cell.mineFlag = !cell.mineFlag
        win = checkWinState(field)
        over = win//TODO checkEndOfGame()
    }

    private fun checkWinState(field: Field): Boolean {
        field.cells.forEach {
            it.forEach { cell ->
                if (cell.mineFlag != cell.mine) return false
            }
        }
        return true
    }

    fun askMarkCoordinates(): Pair<Int, Int> {
        var x = -1
        var y = -1
        while (!isAcceptable(x, y)) {
            print("Set/delete mines marks (x and y coordinates): ")
            x = scanner.nextInt()
            y = scanner.nextInt()
            printIfOpenedCell(field.getCell(x, y))
        }
        return Pair(x, y)
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

    fun isAcceptable(x: Int, y: Int): Boolean {
        return field.existsCell(x, y) &&
                !field.isOpenCell(x, y)
    }

    fun openMineNeighbors() {
        field.cells.forEach {
            it.forEach { cell ->
                if (cell.cntOfmineInNeighbors>0) cell.open = true
            }
        }
    }
}