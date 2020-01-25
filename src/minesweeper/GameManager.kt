package minesweeper

import java.util.Scanner

class GameManager(val field: Field, val scanner: Scanner) {
    var over: Boolean = false
    var win: Boolean = false
    var openFirstCell = false

    fun printField() {
        println(field.cellsToString())
    }

    fun nextStep() {
        val (i, j, op) = askOperation()
        val cell = field.getCell(i, j) ?: return
        when (op) {
            "free" -> openCell(cell)
            "mine" -> setMark(cell)
        }

        win = checkWinState(field)
        over = checkOverState(field)
    }

    fun openCellsWithMines() {
        for (line in field.cells) {
            for (cell in line) {
                if (cell.mine) cell.open = true
            }
        }
    }

    private fun openCell(cell: Cell) {
        if (cell.open) return

        if (!openFirstCell) {
            openFirstCell = true
            if (cell.mine) moveMineFrom(cell)
        }

        cell.open = true
        if (cell.cntOfmineInNeighbors > 0 || cell.mine) {
            return
        }
        val neighbors = field.getNeighbors(cell)
        neighbors.forEach {
            openCell(it)
        }
    }

    private fun moveMineFrom(cell: Cell) {
        val freeCell = findFreeCell(field) ?: return
        cell.mine = false
        freeCell.mine = true
        field.recalculateMineCounters()
    }

    private fun findFreeCell(field: Field): Cell? {
        for (line in field.cells) {
            for (cell in line) {
                if (!cell.mine) return cell
            }
        }
        return null
    }

    private fun setMark(cell: Cell) {
        cell.mineFlag = !cell.mineFlag
    }

    private fun checkWinState(field: Field): Boolean {
        field.cells.forEach {
            it.forEach { cell ->
                if (!cell.open && cell.mineFlag && !cell.mine) return false
                if (cell.open && cell.mine) return false
            }
        }
        return true
    }

    private fun checkOverState(field: Field): Boolean {
        var closeCellsCnt = 0

        for (line in field.cells) {
            for (cell in line) {
                if (cell.open && cell.mine) return true
                if (!cell.open) closeCellsCnt++
            }
        }

        return closeCellsCnt == field.mines
    }

    private fun askOperation(): Triple<Int, Int, String> {
        var i = -1
        var j = -1
        var op = ""

        while (!isAcceptable(i, j)) {
            print("Set/unset mines marks or claim a cell as free: ")
            val x = scanner.nextInt()
            val y = scanner.nextInt()
            op = scanner.next()

            i = y - 1
            j = x - 1

            val cell = field.getCell(i, j)
            cell?.printErrorIfOpen()
        }

        return Triple(i, j, op)
    }

    private fun Cell.printErrorIfOpen() {
        if (this.open) {
            when (this.cntOfmineInNeighbors) {
                0 -> println("Here is an open cell!")
                else -> println("There is a number here!")
            }
        }
    }

    private fun isAcceptable(x: Int, y: Int): Boolean {
        val cell = field.getCell(x, y) ?: return false
        return !cell.open
    }
}
