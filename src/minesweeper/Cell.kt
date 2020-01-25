package minesweeper

class Cell(
        val i: Int,
        val j: Int,
        var mine: Boolean = false,
        var open: Boolean = false,
        var cntOfmineInNeighbors: Int = 0,
        var mineFlag: Boolean = false
)
