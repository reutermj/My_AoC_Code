package d07p01

import java.io.File

class DiGraph constructor (private val edges: Map<String, List<String>>) {
    operator fun get(node: String): List<String>? = edges[node]
}

fun List<Pair<String, String>>.toDiGraph(): DiGraph {
    val edges = mutableMapOf<String, MutableList<String>>()
    for((key, value) in this) {
        val targets = edges.getOrPut(key) { mutableListOf() }
        targets.add(value)
    }
    return DiGraph(edges)
}

fun depthFirstTraversal(diGraph: DiGraph, node: String, traversedNodes: Set<String>): Set<String> =
    diGraph[node]?.fold(traversedNodes) { acc, curr ->
        if(curr in acc) acc
        else depthFirstTraversal(diGraph, curr, acc + curr)
    } ?: traversedNodes

fun main(args: Array<String>) {
    val diGraph =
        File(args[0])
            .readText()
            .replace("""(\.|(bag)s?|[0-9]+)""".toRegex(), "")
            .split("\n")
            .filter { it.isNotEmpty() }
            .map { line ->
                val (lhs, rhs) = line.split("contain").map { it.trim() }
                if (rhs != "no other")
                    rhs.split(",")
                       .map { Pair(it.trim(), lhs) }
                else listOf()
            }
            .flatten()
            .toDiGraph()

    val nodes = depthFirstTraversal(diGraph, "shiny gold", setOf())
    println(nodes.size)
}