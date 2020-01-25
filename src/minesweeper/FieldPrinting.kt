package minesweeper

fun Field.openedToString(): String {
    val printField = StringBuilder()

    printField.appendHeader(size)
    printField.appendDelimiter(size)
    for (i in cells.indices) {
        printField.appendRow(cells[i], i + 1)
    }
    printField.appendDelimiter(size)

    return printField.toString()
}

private fun StringBuilder.appendRow(cells: Array<Cell>, rowNumber: Int) {
    this.append(rowNumber % 10)
    this.append("│")
    cells.joinTo(this, "") { cell ->
        cell.stateToString()
    }
    this.appendln("│")
}

private fun StringBuilder.appendHeader(columns: Int) {
    this.append(" │")
    for (i in 1..columns) {
        this.append(i % 10)
    }
    this.appendln("│")
}

private fun StringBuilder.appendDelimiter(columns: Int) {
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
