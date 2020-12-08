package d03p01

import java.io.File

fun main(args: Array<String>) {
    val slope = mutableListOf<List<Char>>()
    File(args[0]).forEachLine {
        slope.add(it.toList())
    }

    var column = 0
    var treesHit = 0
    for(row in slope) {
        if(row[column % row.size] == '#')
            treesHit++
        column += 3
    }

    println(treesHit)
}