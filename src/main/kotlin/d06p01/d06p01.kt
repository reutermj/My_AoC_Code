package d06p01

import java.io.File

fun main(args: Array<String>) {
    val answers =
        File(args[0])
            .readText()
            .split("\n\n")
            .map { it.replace("\n", "") }
            .map { it.toCharArray().toSet() }
            .map { it.size }
            .sum()
    println(answers)
}