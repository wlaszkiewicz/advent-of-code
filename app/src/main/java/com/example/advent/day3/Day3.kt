package com.example.advent.day3
import com.example.advent.day1.readFile

fun main() {
    val input = readFile("app/src/main/java/com/example/advent/day3/day3.txt")

    println(part1(input))
    part2(input)
}

private fun part1(input: String): String {
    val pattern = """mul\(\d+,\d+\)""".toRegex()

    var totalMul = 0
    for (match in pattern.findAll(input)) {
        val firstNum = match.value.substringAfter("mul(").substringBefore(",").toInt()
        val secondNum = match.value.substringAfter(",").substringBefore(")").toInt()

        totalMul += firstNum * secondNum
    }
    return "The total multiplication is: $totalMul"
}

    private fun part2(input: String) {
        val patternDo = """do\(\)""".toRegex()
        val patternDont = """don't\(\)""".toRegex()

        val dontSections = findAllDontSections(input, patternDo, patternDont)

        // leave only doSections in the input
        var newInput = input
        for (section in dontSections) {
            newInput = newInput.replace(section, "") // remove the dont sections from the input
        }
        print(part1(newInput))
    }

fun findAllDontSections(input: String, patternDo: Regex, patternDont: Regex): MutableList<String> {
    val dontSections = mutableListOf<String>()
    for (match in patternDont.findAll(input)) {
        val startIndex = match.range.first
        val endIndex = indexOfNextDo(input, startIndex, patternDo)
        dontSections.add(input.substring(startIndex, endIndex))
    }
    return dontSections
}

fun indexOfNextDo(input: String, startIndex: Int, patternDo: Regex): Int {
    val nextDo = patternDo.find(input, startIndex)
    return nextDo?.range?.first ?: input.length  // if there is no next do, return the end of the input
}


