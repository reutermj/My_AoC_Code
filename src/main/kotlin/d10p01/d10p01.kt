package d10p01

import utils.*
import java.io.File

fun main(args: Array<String>) {
    val adapters =
        File(args[0])
            .readText().split("\n")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .fold(RadixNone as RadixSort) { acc, i -> acc.add(i) }
            .toList()

    var last = 0
    var one = 0
    var three = 1
    for(adapter in adapters) {
        val dif = adapter - last
        when (dif) {
            1 -> one++
            2 -> {}
            3 -> three++
            else -> {
                println("hmm")
                break
            }
        }
        last = adapter
    }
    println(one * three)
}