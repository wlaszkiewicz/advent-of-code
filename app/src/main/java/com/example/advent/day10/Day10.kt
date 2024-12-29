package com.example.advent.day10

import com.example.advent.day1.readFile

fun main() {
    val inputString: String = readFile("app/src/main/java/com/example/advent/day10/day10.txt")
    val input = inputString.split("\n").map { it.toCharArray().filter { char -> char.isDigit() }.map { char -> char.digitToInt() } }
//    val input = ("89010123\n" +
//            "78121874\n" +
//            "87430965\n" +
//            "96549874\n" +
//            "45678903\n" +
//            "32019012\n" +
//            "01329801\n" +
//            "10456732").split("\n").map { it.toCharArray().map { char -> char.digitToInt() } }

    println(part1(input))

    println(part2(input))

}

fun part1(input: List<List<Int>>): String {
    val trailheadsIndexes = getTrailheads(input)
    var sum = 0
    for (i in trailheadsIndexes) {
        sum += getScore(input, i)
    }

    return "The sum of the scores of all trailheads is: $sum"

}

fun getScore(input: List<List<Int>>, start: Pair<Int, Int>): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val leftToCheck = mutableListOf<Pair<Int, Int>>()
    val reachableNines = mutableSetOf<Pair<Int, Int>>()

    leftToCheck.add(start)
    visited.add(start)

    while (leftToCheck.isNotEmpty()) {
        val current = leftToCheck.removeAt(0) // remove the element that we are currently checking
        val (x, y) = current
        val currentHeight = input[x][y]

        if (currentHeight == 9) {
            reachableNines.add(current)
            continue
        }

        val toCheck = listOf(
            Pair(x - 1, y), // up
            Pair(x + 1, y), // down
            Pair(x, y - 1), // left
            Pair(x, y + 1)  // right
        )

        for (place in toCheck) {
            val newX = place.first
            val newY = place.second
            if (newX in input.indices && newY in 0 until input[0].size && !visited.contains(place)) {
                val newHeight = input[newX][newY]
                if (newHeight == currentHeight + 1) {
                    leftToCheck.add(place)
                    visited.add(place)
                }
            }
        }
    }

    return reachableNines.size
}


private fun getTrailheads(input: List<List<Int>>): MutableList<Pair<Int,Int>> {
    val trailheadsIndexes = mutableListOf<Pair<Int,Int>>()
    for (i in input.indices) {
        for (j in input[i].indices) {
            if (input[i][j] == 0) {
                trailheadsIndexes.add(Pair(i,j))
            }
        }
    }
    println(trailheadsIndexes)
    return trailheadsIndexes
}

fun part2(input: List<List<Int>>): String {
    val trailheadsIndexes = getTrailheads(input)
    var ratings = 0
    for (i in trailheadsIndexes) {
        ratings += getRating(input, i)
    }

    return "The sum of the ratings of all trailheads is: $ratings"
}

fun getRating(input: List<List<Int>>, start: Pair<Int, Int>): Int {
    val leftToCheck = mutableListOf<Pair<Pair<Int, Int>, List<Pair<Int, Int>>>>()
    leftToCheck.add(Pair(start, listOf(start))) // add the start point and the path to it

    var distinctTrails = 0

    while (leftToCheck.isNotEmpty()) {
        val (current, path) = leftToCheck.removeAt(0) // remove the element that we are currently checking
        val (x, y) = current
        val currentHeight = input[x][y]

        if (currentHeight == 9) {
            distinctTrails++
            continue
        }

        val toCheck = listOf(
            Pair(x - 1, y), // up
            Pair(x + 1, y), // down
            Pair(x, y - 1), // left
            Pair(x, y + 1)  // right
        )

        for (place in toCheck) {
            val newX = place.first
            val newY = place.second
            if (newX in input.indices && newY in 0 until input[0].size && place !in path) {
                val newHeight = input[newX][newY]
                if (newHeight == currentHeight + 1) {
                    leftToCheck.add(Pair(place, path + place))
                }
            }
        }
    }

    return distinctTrails
}