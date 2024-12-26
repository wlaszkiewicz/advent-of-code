package com.example.advent.day7

import com.example.advent.day1.readFile

fun main() {
    val input: String =
        readFile("app/src/main/java/com/example/advent/day7/day7.txt")

    println(part1(input))
    println(part2(input))
}

private fun part1(input: String): String {
    return calculateSum(input, listOf("+", "*"))
}

private fun part2(input: String): String {
    return calculateSum(input, listOf("+", "*", "||"))
}

private fun calculateSum(input: String, operators: List<String>): String {
    val results: MutableList<Long> = input.lines().map { it.substringBefore(":").toLong() }.toMutableList()
    val numbers = input.lines().map { it.substringAfter(": ").split(" ").map { it.toLong() } }

    var sum = 0L
    for (result in results) {
        if (possibleEquation(result, numbers[results.indexOf(result)], operators)) {
            sum += result
        }
    }

    return "Total calibration result: $sum"
}

fun possibleEquation(result: Long, numbers: List<Long>, operators: List<String>): Boolean {
    val operatorCombinations = generateAllOperatorCombinations(numbers.size - 1, operators)
    for (combination in operatorCombinations) {
        if (evaluateExpression(numbers, combination) == result) {
            return true
        }
    }
    return false
}

fun evaluateExpression(numbers: List<Long>, operators: List<String>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        when (operators[i - 1]) {
            "+" -> result += numbers[i]
            "*" -> result *= numbers[i]
            "||" -> result = (result.toString() + numbers[i].toString()).toLong()
        }
    }
    return result
}

fun generateAllOperatorCombinations(size: Int, operators: List<String>): List<List<String>> {
    if (size == 0) return listOf(emptyList()) // Base case for recursion
    val smallerCombinations = generateAllOperatorCombinations(size - 1, operators)
    val result = mutableListOf<List<String>>()
    for (combination in smallerCombinations) {
        for (operator in operators) {
            result.add(combination + operator)
        }
    }
    return result
}