package d02p01

import java.io.File

fun main(args: Array<String>) {
    var numPassingPasswords = 0
    File(args[0]).forEachLine { line ->
        val (range, rule, password) = line.split(" ")
        val (low, high) = range.split("-").map { it.toInt() }
        val occurrences = password.fold(0) { acc, c -> acc + if (c == rule[0]) 1 else 0 }
        if (occurrences in low..high)
            numPassingPasswords++
    }
    println(numPassingPasswords)
}