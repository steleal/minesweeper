package minesweeper

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    println("How many mines do you want on the field?")
    val amountOfMines = scanner.nextInt()

    val field = Field(9, amountOfMines)

    print(field.allToString())
}
