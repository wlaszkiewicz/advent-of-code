package com.example.advent.day11

import com.example.advent.day1.readFile

private val memo: MutableMap<String, Long> = mutableMapOf()
private val input2: List<Long> = readFile("app/src/main/java/com/example/advent/day11/day11.txt").split(" ").map { it.toLong() }
private val input: List<String> = readFile("app/src/main/java/com/example/advent/day11/day11.txt").split(" ")
fun main() {
    //val input = "125 17".split(" ")

     println(part1())
     println(part2())
}

fun part1(): String {
    //If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.
    //If the stone is engraved with a number that has an even number of digits, it is replaced by two stones. The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved on the new right stone. (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
    //If none of the other rules apply, the stone is replaced by a new stone; the old stone's number multiplied by 2024 is engraved on the new stone

    var newStones: MutableList<String> = input.toMutableList()
    for (i in 0 until 25) {
        newStones = replaceStone(newStones)
    }
    return "The number of stones after 25 blinks is ${newStones.size}"

}

fun replaceStone(stones: MutableList<String>): MutableList<String> {
    val newStones = mutableListOf<String>()
    for (stone in stones) {
        if (stone == "0") {
            newStones.add("1")
        } else if (stone.length % 2 == 0) {
            val half = stone.length / 2
            newStones.add(stone.substring(0, half))
            newStones.add(stone.substring(half).toLong().toString()) //remove leading zeroes
        } else {
            newStones.add((stone.toLong() * 2024).toString())
        }
    }
    return newStones
}


fun part2(): String {
    return "The number of stones after 75 blinks is ${calculateStones()}"
}

private fun calculateStones(): Long {
    var result: Long = 0
    for (i in input2) {
        result += replaceStonesRecursion(75, i)
    }
    return result
}

private fun replaceStonesRecursion(blinks: Int, number: Long): Long { // recursion and memoization
    if (blinks == 0) { // base case
        return 1
    }
    val key = "$number at $blinks"

    if (memo.containsKey(key)) { // memoization to avoid recalculating the same values
        return memo[key]!!
    }

    val digits = number.toString().length
    if (number == 0L) {
        val result = replaceStonesRecursion(blinks - 1, 1)
        memo[key] = result
        return result
    } else if (digits % 2 == 0) {
        val half = digits / 2
        val first = number.toString().substring(0, half).toLong()
        val second = number.toString().substring(half).toLong()
        val result = replaceStonesRecursion(blinks - 1, first) + replaceStonesRecursion(blinks - 1, second)
        memo[key] = result
        return result
    } else {
        val result = replaceStonesRecursion(blinks - 1, number * 2024)
        memo[key] = result
        return result
    }
}
