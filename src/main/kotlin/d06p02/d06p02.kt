package d06p02

import java.io.File

fun main(args: Array<String>) {
    val universalSet = ('a'..'z').toSet()
    val answers =
        File(args[0])
            .readText()
            .split("\n\n")
            .map { lines ->
                lines.split("\n")
                     .map { it.toCharArray().toSet() }
                     .filter { it.isNotEmpty() }
                     .fold(universalSet) { acc, set -> acc.intersect(set) }
                     .size
            }
            .sum()
    println(answers)
}