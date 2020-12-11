package d07p02

import java.io.File

class DiGraph constructor (private val edges: Map<String, List<Pair<Int, String>>>) {
    operator fun get(node: String): List<Pair<Int, String>>? = edges[node]
}

fun List<Pair<String, Pair<Int, String>>>.toDiGraph(): DiGraph {
    val edges = mutableMapOf<String, MutableList<Pair<Int, String>>>()
    for((key, value) in this) {
        val targets = edges.getOrPut(key) { mutableListOf() }
        targets.add(value)
    }
    return DiGraph(edges)
}

fun depthFirstTraversal(diGraph: DiGraph, node: String): Long =
    (diGraph[node]?.fold(0L) { acc, (num, curr) ->
        acc + num * depthFirstTraversal(diGraph, curr)
    } ?: 0) + 1

fun main(args: Array<String>) {
    val diGraph =
        File(args[0])
            .readText()
            .replace("""(\.|(bag)s?)""".toRegex(), "")
            .split("\n")
            .filter { it.isNotEmpty() }
            .map { line ->
                val (lhs, rhs) = line.split("contain").map { it.trim() }
                if (rhs != "no other")
                    rhs.split(",")
                       .map {
                           val regex = """(?<num>\d+) (?<rest>.+)""".toRegex()
                           val groups = regex.matchEntire(it.trim())?.groups!!
                           Pair(lhs, Pair(groups["num"]!!.value.toInt(), groups["rest"]!!.value.trim()))
                       }
                else listOf()
            }
            .flatten()
            .toDiGraph()

    val nums = depthFirstTraversal(diGraph, "shiny gold") - 1
    println(nums)
}