package d08p01

import java.io.File

fun main(args: Array<String>) {
    val instructions =
        File(args[0]).readText().split("\n")
            .filter { it.isNotEmpty() }
            .map { it.trim().split(" ") }
            .map { (op, sigNum) ->
                val sign = if(sigNum[0] == '+') 1 else -1
                val num = sigNum.substring(1).toInt()
                Pair(op, sign * num)
            }

    val isInstructionProcessed = Array(instructions.size) { false }

    var instructionPointer = 0
    var accumulator = 0
    while(instructionPointer < instructions.size) {
        if(isInstructionProcessed[instructionPointer]) {
            println(accumulator)
            break
        }

        isInstructionProcessed[instructionPointer] = true

        val (op, num) = instructions[instructionPointer]
        when(op) {
            "nop" -> instructionPointer++
            "jmp" -> instructionPointer += num
            "acc" -> {
                accumulator += num
                instructionPointer++
            }
        }
    }
}