package d09p02

import java.io.File

fun main(args: Array<String>) {
    val numbers =
        File(args[0])
            .readText().split("\n")
            .filter { it.isNotEmpty() }
            .map { it.toLong() }

    val target = 258585477L
    var low = 0
    var high = 1
    var sum = numbers[0] + numbers[1]

    while (high < numbers.size) {
        if(sum == target) {
            var min = Long.MAX_VALUE
            var max = Long.MIN_VALUE
            for(i in low..high) {
                min = minOf(min, numbers[i])
                max = maxOf(max, numbers[i])
            }
            print(min+max)
            break
        }
        else if ((sum < target) or (low == high - 1)) {
            high++
            sum += numbers[high]
        }
        else {
            sum -= numbers[low]
            low++
        }
    }
}