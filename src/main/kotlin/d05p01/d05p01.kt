package d05p01

import java.io.File

fun main(args: Array<String>) {
    var maxSeatId = 0
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
        if(seatId > maxSeatId) maxSeatId = seatId
    }
    println(maxSeatId)
}