package d04p02

import java.io.File
import java.lang.Exception

val eyeColors = hashSetOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

fun isValid(key: String, value: String): Boolean {
    return try {
        when(key) {
            "byr" -> value.toInt() in 1920..2020
            "iyr" -> value.toInt() in 2010..2020
            "eyr" -> value.toInt() in 2020..2030
            "hgt" -> {
                val regex = """(?<num>\d+)(?<unit>(in|cm))""".toRegex()
                val groups = regex.matchEntire(value)?.groups
                val num = groups?.get("num")?.value?.toInt()
                val unit = groups?.get("unit")?.value
                when(unit) {
                    "in" -> num in 59..76
                    "cm" -> num in 150..193
                    else -> false
                }
            }
            "hcl" -> value.matches("^#[0-9a-fA-F]{6}$".toRegex())
            "ecl" -> value in eyeColors
            "pid" -> value.matches("^[0-9]{9}$".toRegex())
            else -> true
        }
    } catch (_: Exception) {
        false
    }
}

fun main(args: Array<String>) {
    val benchmark = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    val valid =
        File(args[0])
            .readText()
            .split("\n\n")
            .map { it.replace('\n', ' ') }
            .map { line ->
                line.split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.split(":") }
            }
            .map { splits ->
                val containsKeys = splits.map { it[0] }.containsAll(benchmark)
                val validatedKeys = splits.fold(true) { acc, split -> acc and isValid(split[0], split[1]) }
                if(containsKeys and validatedKeys) 1
                else 0
            }
            .sum()
    print(valid)
}