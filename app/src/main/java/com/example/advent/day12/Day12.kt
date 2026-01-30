package com.example.advent.day12

import com.example.advent.day1.readFile

lateinit var input: Array<String>

fun main() {
    val inputList: MutableList<String> = readFile("app/src/main/java/com/example/advent/day12/day12.txt").split("\n").toMutableList()

    inputList.replaceAll { it.replace("\r", "") } // remove carriage return

    input = inputList.toTypedArray()

//val input =("RRRRIICCFF\n" +
//        "RRRRIICCCF\n" +
//        "VVRRRCCFFF\n" +
//        "VVRCCCJFFF\n" +
//        "VVVVCJJCFE\n" +
//        "VVIVCCJJEE\n" +
//        "VVIIICJJEE\n" +
//        "MIIIIIJJEE\n" +
//        "MIIISIJEEE\n" +
//        "MMMISSJEEE").split("\n").toTypedArray()

    println(part1())
    //println(part2()) //i gave up :)))))

}

fun part1(): String {
//    for (r in input.indices) {
//        println(input[r].length)
//    }

    val visited: MutableList<Pair<Int,Int>> = mutableListOf()
    var totalPrice = 0
    for (row in input.indices) {
        for (column in 0 until input[0].length) {
            if (!visited.contains(Pair(row, column))) {
                val (area, perimeter) = check(row, column, input, visited)
                totalPrice += area * perimeter
            }
        }
    }

    return "The total price of fencing: $totalPrice"
}

fun check(row: Int, column: Int, input: Array<String>, visited: MutableList<Pair<Int,Int>>): Pair<Int, Int> {
    val directions = arrayOf(
        Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1)
    )
    var area = 0
    var perimeter = 0
    val toVisit: MutableList<Pair<Int, Int>> = mutableListOf()

    toVisit.add(Pair(row, column))
    visited.add(Pair(row, column))
    val plantType = input[row][column]

    while (toVisit.isNotEmpty()) {
        val (x, y) = toVisit.removeAt(0)
        area += 1
        var plantPerimeter = 4

        for ((changeX, changeY) in directions) {
            val newX = x + changeX
            val newY = y + changeY

            if (newX in input.indices && (newY in 0 until input[0].length )) {
                if (input[newX][newY] == plantType) {
                    plantPerimeter -= 1 // shared side
                    if (!visited.contains(Pair(newX, newY))) {
                        visited.add(Pair(newX, newY))
                        toVisit.add(Pair(newX, newY))
                    }
                }
            }
        }
        perimeter += plantPerimeter
    }
    return Pair(area, perimeter)
}