package minesweeper

import processor.ConsoleHelper
import processor.IOHelper
import java.util.Scanner

fun main() {
    val helper = ConsoleHelper(Scanner(System.`in`))

    val field = initField(helper)
    val game = GameManager(field, helper)

    while (!game.over) {
        game.printField()
        game.nextStep()
    }

    if (game.win) {
        game.printField()
        congratulation(helper)
    } else {
        game.openCellsWithMines()
        game.printField()
        sorry(helper)
    }

}

private fun sorry(helper: IOHelper) {
    helper.print("You stepped on a mine and failed!")
}

private fun congratulation(helper: IOHelper) {
    helper.print("Congratulations! You found all mines!")
}

private fun initField(helper: IOHelper): Field {
    helper.print("How many mines do you want on the field?")
    return Field(
            size = 9,
            mines = helper.askInt()
    )
}
