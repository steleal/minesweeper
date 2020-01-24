package minesweeper

class Cell(
        var mine: Boolean = false,
        var open: Boolean = false,
        var cntOfmineInNeighbors: Int = 0,
        var mineFlag: Boolean = false
)
