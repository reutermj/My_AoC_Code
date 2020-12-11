package d08p02

import java.io.File

enum class Colors {
    White,
    Green,
    Red
}

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

    val instructionColors = Array(instructions.size) { Colors.White }

    var state = 0

    var instructionPointer = 0
    var accumulator = 0

    val backtrackInstructions = mutableListOf<Int>()
    var backtrackAccumulator = 0
    var backtrackInstructionPointer = 0

    while(instructionPointer < instructions.size) {
        val (op, num) = instructions[instructionPointer]

        when (state) {
            0 -> { //no assumption made
                if(instructionColors[instructionPointer] == Colors.Red) { // found the instruction that needs to be flipped
                    state = 2
                    when(op) {
                        "nop" -> instructionPointer += num
                        "jmp" -> instructionPointer++
                    }
                } else { // assume that this instruction is to be flipped and backtrack to it if this assumption is false
                    instructionColors[instructionPointer] = Colors.Green
                    when(op) {
                        "nop" -> { //assume that this is the incorrect node, but set the backtrack node to the next one
                            state = 1
                            backtrackAccumulator = accumulator
                            backtrackInstructionPointer = instructionPointer + 1
                            instructionPointer += num
                        }
                        "jmp" -> { //assume that this is the incorrect node, but set the backtrack node by the offset
                            state = 1
                            backtrackAccumulator = accumulator
                            backtrackInstructionPointer = instructionPointer + num
                            instructionPointer++
                        }
                        "acc" -> {
                            accumulator += num
                            instructionPointer++
                        }
                    }
                }
            }
            1 -> { //assumption is made
                if(instructionColors[instructionPointer] != Colors.White) { // found infinite loop. backtrack to assumption
                    backtrackInstructions.forEach { instructionColors[it] = Colors.Red }
                    backtrackInstructions.clear()
                    accumulator = backtrackAccumulator
                    instructionPointer = backtrackInstructionPointer
                    state = 0
                }
                else { // continue processing nodes under the assumption
                    instructionColors[instructionPointer] = Colors.Green
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
            2 -> { //correct node found, iterate through the rest of the instructions to get result
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
    }
    println(accumulator)
}