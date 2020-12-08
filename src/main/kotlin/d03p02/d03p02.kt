package d03p02

import java.io.File

fun main(args: Array<String>) {
    val slope = mutableListOf<List<Char>>()
    File(args[0]).forEachLine {
        slope.add(it.toList())
    }

    var multipliedTreesHit = 1L

    val slopes = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))

    for ((run, rise) in slopes) {
        var column = 0
        var treesHit = 0

        for(rowNum in 0 until slope.size step rise) {
            val row = slope[rowNum]
            if(row[column % row.size] == '#')
                treesHit++
            column += run
        }

        multipliedTreesHit *= treesHit
    }

    println(multipliedTreesHit)
}