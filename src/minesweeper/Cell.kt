package minesweeper

class Cell(
        val row: Int,
        val column: Int,
        var mine: Boolean = false,
        var open: Boolean = false,
        var cntOfmineInNeighbors: Int = 0,
        var mineFlag: Boolean = false
)
