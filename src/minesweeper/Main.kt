package minesweeper

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    val field = initField(scanner)
    val game = GameManager(field,scanner)

    while (!game.over) {
        game.printField()
        game.nextStep()
    }

    when {
        game.win -> congratulation()
        else -> sorry()
    }

}

private fun sorry() {
    println("You lose!")
}

private fun congratulation() {
    println("Congratulations! You found all mines!")
}

private fun initField(scanner: Scanner): Field {
    println("How many mines do you want on the field?")
    return Field(
            size = 9,
            mines = scanner.nextInt()
    )
}
