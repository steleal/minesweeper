package minesweeper

class Cell(
        var value: CellValue,
        var state: CellState = CellState.CLOSE,
        var cntOfmineInNeighbors: Int = 0
) {
    fun isMine(): Boolean = value == CellValue.MINE
    fun isOpen(): Boolean = state == CellState.OPEN
}

enum class CellState {
    OPEN,
    CLOSE
}

enum class CellValue {
    MINE,
    EMPTY
}
