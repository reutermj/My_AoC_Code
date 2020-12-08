package d05p02

import java.io.File

fun main(args: Array<String>) {
    //dont look at me. im ugly :(
    val isSeatOpen = Array<Boolean>(1024) {
        when (it) {
            in 0 until 8 -> false
            in 1020 until 1028 -> false
            else -> true
        }
    }

    File(args[0]).forEachLine { line ->
        var row = 0
        for (c in line.substring(0, 7)) {
            row = row shl 1
            if (c == 'B') row = row or 1
        }

        var col = 0
        for (c in line.substring(7)) {
            col = col shl 1
            if (c == 'R') col = col or 1
        }

        val seatId = row * 8 + col
        isSeatOpen[seatId] = false
    }

    for(i in 0 until 1024) {
        if(isSeatOpen[i]) println(i)
    }
}