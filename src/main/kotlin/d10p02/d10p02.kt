package d10p02

import utils.*
import java.io.File

fun main(args: Array<String>) {
    val adapters =
        listOf(0) +
        File(args[0])
            .readText().split("\n")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .fold(RadixNone as RadixSort) { acc, i -> acc.add(i) }
            .toList()

    val pathCounts = Array(adapters.size) { 0L }
    pathCounts[pathCounts.size - 1] = 1L
    pathCounts[pathCounts.size - 2] = 1L
    if(adapters[adapters.size - 1] - adapters[adapters.size - 3] <= 3)
        pathCounts[pathCounts.size - 3] = 2L
    else
        pathCounts[pathCounts.size - 3] = 1L

    for (i in (adapters.size - 4) downTo 0) {
        val adapter = adapters[i]

        if(adapters[i + 1] - adapter <= 3) pathCounts[i] += pathCounts[i + 1]
        if(adapters[i + 2] - adapter <= 3) pathCounts[i] += pathCounts[i + 2]
        if(adapters[i + 3] - adapter <= 3) pathCounts[i] += pathCounts[i + 3]
    }

    println(pathCounts[0])
}