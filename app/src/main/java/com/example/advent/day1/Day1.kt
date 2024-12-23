package com.example.advent.day1

import java.io.File

fun main() {
    val input = readFile("app/src/main/java/com/example/advent/day1/day1.txt")
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    val numLines = input.lines().size
    addToList1(input, list1, numLines)
    addToList2(input, list2, numLines)

    println(part1(list1, list2))

    addToList1(input, list1, numLines)
    addToList2(input, list2, numLines)

    println(part2(list1, list2))


}

private fun part2(list1: MutableList<Int>, list2: MutableList<Int>): String {
    var similarity = 0
    for (i in 0 until list1.size) {
        val num = list1[i]
        var countNum = 0
        for (j in 0 until list2.size) {
            val num2 = list2[j]
            if (num == num2) {
                countNum += 1
            }
        }
        similarity += countNum * num
    }
    return "The similarity is: $similarity"
}


private fun part1(list1: MutableList<Int>, list2: MutableList<Int>): String {
    var totalDistance = 0
    for (i in 0 until list1.size) {
        val smallestNum1 = list1.minOrNull()
        list1.remove(smallestNum1)
        val smallestNum2 = list2.minOrNull()
        list2.remove(smallestNum2)

        if (smallestNum2 != null && smallestNum1 != null) {
        totalDistance += kotlin.math.abs(smallestNum1 - smallestNum2)
        }
    }
    return "The total distance is: $totalDistance"
}

fun addToList1(input: String, list: MutableList<Int>, numLines: Int) {
    for (i in 0 until numLines) {
        list.add(input.lines()[i].substringBefore(" ").trim().toInt())  // add the first number to the list and remove any leading or trailing whitespace
    }
}
fun addToList2(input: String, list: MutableList<Int>, numLines: Int) {
    for (i in 0 until numLines) {
        list.add(input.lines()[i].substringAfter(" ").trim().toInt())  // add the second number to the list
    }
}

fun readFile(path: String): String {
    val file = File(path)
    if (file.exists()) {
        return file.readText()
    } else {
        throw Exception("File not found at path: $path")
    }
}
