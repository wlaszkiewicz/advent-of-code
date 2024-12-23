package com.example.advent.day1

import java.io.File

fun main() {
    val input = readFile("app/src/main/java/com/example/advent/day1/day1.txt")
    val list1 = mutableListOf<String>()
    val list2 = mutableListOf<String>()
    val numLines = input.lines().size
    addToList1(input, list1, numLines)
    addToList2(input, list2, numLines)

    val totalDistance = getTotalDistance(list1, list2)
    println("The total distance is: $totalDistance")
}

private fun getTotalDistance(list1: MutableList<String>, list2: MutableList<String>): Int {
    var totalDistance = 0
    for (i in 0 until list1.size) {
        val smallestNum1 = list1.minOrNull()
        list1.remove(smallestNum1)
        val smallestNum2 = list2.minOrNull()
        list2.remove(smallestNum2)

        totalDistance += findDistance(smallestNum1, smallestNum2)
    }
    return totalDistance
}

fun findDistance(smallestNum1: String?, smallestNum2: String?): Int {
    var distance = 0
    if (smallestNum1 != null && smallestNum2 != null) {
        if (smallestNum1.toInt() > smallestNum2.toInt()) {
            distance = smallestNum1.toInt() - smallestNum2.toInt()
        } else {
            distance = smallestNum2.toInt() - smallestNum1.toInt()
        }
    } else {
        throw Exception("One of the numbers is null")
    }
    return distance

}

fun addToList1(input: String, list: MutableList<String>, numLines: Int) {
    for (i in 0 until numLines) {  // iterate through the lines
        list.add(input.lines()[i].substringBefore(" ").trim())  // add the first number to the list and remove any leading or trailing whitespace
    }
}
fun addToList2(input: String, list: MutableList<String>, numLines: Int) {
    for (i in 0 until numLines) {
        list.add(input.lines()[i].substringAfter(" ").trim())  // add the second number to the list
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
