package processor

import java.util.Scanner

class ConsoleHelper(private val scanner: Scanner) : IOHelper {

    override fun print(msg: String) = println(msg)

    override fun askString(msg: String) = askForInput(scanner::next, msg)

    override fun askLine(msg: String) = askForInput(scanner::nextLine, msg)

    override fun askInt(msg: String) = askForInput(scanner::nextInt, msg)

    override fun askDouble(msg: String) = askForInput(scanner::nextDouble, msg)

    private fun <T> askForInput(getInput: () -> T, msg: String = ""): T {
        if (msg != "") this.print(msg)
        return getInput()
    }
}
