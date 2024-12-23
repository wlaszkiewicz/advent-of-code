package com.example.advent.day2
import com.example.advent.day1.readFile

fun main() {
    val input = readFile("app/src/main/java/day2/day2.txt")

    val inputList:  MutableList<List<Int>> = mutableListOf()
    for (line in input.lines()) {
            val lineList = line.split(" ").map { it.toInt() } // split the line by spaces and convert each element to an integer
            inputList.add(lineList) // add the list of integers to the inputList
    }
    println(part1(inputList))

    println(part2(inputList))

}

fun part1(inputList: MutableList<List<Int>>): String {
    var count = 0
    for (i in 0 until inputList.size) {
        if (isfSafe(inputList[i])) {
            count += 1
        }
    }
    return "The number of safe reports is: $count"
}

fun part2(inputList: MutableList<List<Int>>): String {
    val notSafeReports = mutableListOf<List<Int>>()
    for (i in 0 until inputList.size) {
        if (!isfSafe(inputList[i])) {
            notSafeReports.add(inputList[i])
        }
    }
    var count = 0
        for (i in 0 until notSafeReports.size) {
            if(isSafeIfOneNumberRemoved(notSafeReports[i])){
                count += 1
                }
        }

    return "The number of new safe reports when we tolerate a single bad level: ${count}"
}

fun isfSafe(inputLine: List<Int>): Boolean {
    return (isDecreasing(inputLine) || isIncreasing(inputLine)) && adjacentNumbers(inputLine)
}

fun isDecreasing(list: List<Int>): Boolean {
    for (i in 0 until list.size - 1) {
        if (list[i] > list[i + 1]) {
            return false
        }
    }
    return true
}

fun isIncreasing(list: List<Int>): Boolean {
    for (i in 0 until list.size - 1) {
        if (list[i] < list[i + 1]) {
            return false
        }
    }
    return true
}

fun adjacentNumbers(list: List<Int>): Boolean {
    for (i in 0 until list.size - 1) {
        if (list[i] == list[i+1] || kotlin.math.abs(list[i] - list[i + 1]) > 3) { // if the two numbers are the same or the difference between them is greater than 3
            return false
        }
    }
    return true
}

fun isSafeIfOneNumberRemoved(list: List<Int>): Boolean {
    for (i in list.indices) {
        val newList = list.toMutableList()
        newList.removeAt(i)
        if (isfSafe(newList)) {
            return true
        }
    }
    return false
}


