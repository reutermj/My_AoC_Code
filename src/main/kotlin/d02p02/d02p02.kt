package d02p02

import java.io.File

fun main(args: Array<String>) {
    var numPassingPasswords = 0
    File(args[0]).forEachLine { line ->
        val (times, rule, password) = line.split(" ")
        val (pos1, pos2) = times.split("-").map { it.toInt() - 1 }
        if ((password[pos1] == rule[0]) xor (password[pos2] == rule[0]))
            numPassingPasswords++
    }
    println(numPassingPasswords)
}