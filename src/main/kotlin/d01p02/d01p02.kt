package d01p02

import java.io.File

fun step(i: Int, m: Int, a: Array<Boolean>): Int {
    var j = i
    while (!a[j]) {
        j += m
    }
    return j
}

fun main(args: Array<String>) {
    val a = Array(2021) { false }
    File(args[0]).forEachLine { l ->
        val n = l.toInt()
        if (n <= 2020) {
            a[n] = true
        }
    }

    var l = step(0, 1, a)
    var m = step(l + 1, 1, a)
    var r = step(2020, -1, a)

    while (l < r) {
        while (m < r) {
            when {
                l + m + r == 2020 -> {
                    println(l * m * r)
                    return
                }
                l + m + r > 2020 -> r = step(r - 1, -1, a)
                l + m + r < 2020 -> m = step(m + 1, 1, a)
            }
        }
        l = step(l + 1, 1, a)
        m = step(l + 1, 1, a)
    }

    println("You done fucked up")
}