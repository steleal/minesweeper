package minesweeper
import java.lang.StringBuilder
import java.util.Scanner
import kotlin.random.Random

fun main() {
    val size = 9
    val blank = '.'
    val mine = 'X'
    val scanner = Scanner(System.`in`)
    println("How many mines do you want on the field?")
    val amountOfMines = scanner.nextInt()
    val field = Array<CharArray>(size) {CharArray(size) {blank}}

    repeat(amountOfMines) {
        var notAdded = true
        while (notAdded) {
            val row = Random.nextInt(size)
            val column = Random.nextInt(size)
            if (field[row][column] == blank) {
                field[row][column] = mine
                notAdded = false
            }
        }
    }

    val printField = StringBuilder()
    field.forEach {
        it.joinTo(printField,"")
        printField.appendln()
    }
    print(printField)
}
