package d04p01

import java.io.File

fun main(args: Array<String>) {
    val benchmark = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    val valid =
        File(args[0])
            .readText()
            .split("\n\n")
            .map { it.replace('\n', ' ') }
            .map { line ->
                line.split(" ")
                    .map { it.split(":")[0] }
                    .containsAll(benchmark)
            }
            .map { if(it) 1 else 0 }
            .sum()
    print(valid)
}