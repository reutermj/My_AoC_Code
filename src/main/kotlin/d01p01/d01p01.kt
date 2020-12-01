package d01p01

import java.io.File

fun step(i: Int, m: Int, a: Array<Boolean>): Int {
    var j = i
    while (!a[j]) {
        j += m
    }
    return j
}

fun main(args: Array<String>) {
    val a = Array(2021) { _ -> false }
    File(args[0]).forEachLine { l ->
        val n = l.toInt()
        if (n <= 2020) {
            a[n] = true
        }
    }

    var l = step(0, 1, a)
    var r = step(2020, -1, a)

    while (l < r) {
        when {
            l + r == 2020 -> {
                println(l * r)
                return
            }
            l + r > 2020 -> r = step(r - 1, -1, a)
            l + r < 2020 -> l = step(l + 1, 1, a)
        }
    }

    println("You done fucked up")
}