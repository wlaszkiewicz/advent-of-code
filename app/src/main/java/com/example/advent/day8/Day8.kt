package com.example.advent.day8

import com.example.advent.day1.readFile
import java.io.File
import kotlin.math.abs
import kotlin.math.roundToInt


fun main() {
    var input: String = readFile("app/src/main/java/com/example/advent/day8/day8.txt")

    input = input.replace("\r", "")

//    var input = "##....#....#\n" +
//                ".#.#....0...\n" +
//                "..#.#0....#.\n" +
//                "..##...0....\n" +
//                "....0....#..\n" +
//                ".#...#A....#\n" +
//                "...#..#.....\n" +
//                "#....#.#....\n" +
//                "..#.....A...\n" +
//                "....#....A..\n" +
//                ".#........#.\n" +
//                "...#......##"
//    input = input.replace("#", ".")

    println(part1(input))
    println(part2(input))

}

fun part1(input: String): String {
    val antennas = getUniqueAntennas(input)
    val inputArray = input.split("\n").map { it.toCharArray() }.toTypedArray()

    val antinodes = mutableSetOf<Pair<Int, Int>>()

    for (antenna in antennas) {
        val allAntennas = findAllAntennasOfTheType(inputArray, antenna)
        val pairs = groupAntennas(allAntennas)
        for (pair in pairs) {
            val antinodeIndexes = findAntinodeIndexes(pair.first, pair.second)
            for (index in antinodeIndexes) {
                if (validSpace(inputArray, index)) {
                    antinodes.add(index)
                }
            }
        }
    }

    return "Unique locations with an antinode: ${antinodes.size}"
}

fun getUniqueAntennas(input: String): MutableSet<Char> {
    val antennas: MutableSet<Char> = mutableSetOf()
    for (char in input) {
        if (char.isLetterOrDigit()) {
            antennas.add(char)
        }
    }
    return antennas
}

fun findAntinodeIndexes(first: Pair<Int, Int>, second: Pair<Int, Int>): List<Pair<Int, Int>> {
    val antinode1 = Pair(2 * first.first - second.first, 2 * first.second - second.second)
    val antinode2 = Pair(2 * second.first - first.first, 2 * second.second - first.second)

    return listOf(antinode1, antinode2)
}

private fun validSpace(input: Array<CharArray>, index: Pair<Int, Int>): Boolean {
    val (x, y) = index
    return x in input.indices && y in input[0].indices && input[x][y] != '#'
}

 fun findAllAntennasOfTheType(input: Array<CharArray>, antenna: Char): MutableList<Pair<Int, Int>>  {
    val allAntennas = mutableListOf<Pair<Int, Int>>()
    for (i in input.indices) {
        for (j in input[i].indices) {
            if (input[i][j] == antenna) {
                allAntennas.add(Pair(i, j))
            }
        }
    }
    return allAntennas
}


 fun groupAntennas(allAntennas: MutableList<Pair<Int, Int>>): MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
    val pairedUpAntennas = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    for (i in allAntennas.indices) {
        for (j in i + 1 until allAntennas.size) {
            pairedUpAntennas.add(Pair(allAntennas[i], allAntennas[j]))
        }
    }
    return pairedUpAntennas
}

fun part2(input: String): String {
    val antennas = getUniqueAntennas(input)
    val inputArray = input.split("\n").map { it.toCharArray() }.toTypedArray()

    val antinodes = mutableSetOf<Pair<Int, Int>>()

    for (antenna in antennas) {
        val allAntennas = findAllAntennasOfTheType(inputArray, antenna)
        val pairs = groupAntennas(allAntennas)

        for (pair in pairs) {
            val antinodeIndexes = findAntinodesAlongLine(pair.first, pair.second, inputArray)
            antinodes.addAll(antinodeIndexes)
        }

    }
    //printTheOutput(antennas, inputArray, antinodes) // for debugging

    return "Unique locations with an antinode: ${antinodes.size}"
}

private fun printTheOutput(antennas: MutableSet<Char>, inputArray: Array<CharArray>, antinodes: MutableSet<Pair<Int, Int>>) {
    for (antenna in antennas) {
        val allAntennas = findAllAntennasOfTheType(inputArray, antenna)
        for (antennas in allAntennas) {
            inputArray[antennas.first][antennas.second] = antenna
        }
    }

    for (antinode in antinodes) {
        if (inputArray[antinode.first][antinode.second] == '.') {
            inputArray[antinode.first][antinode.second] = '#' // Mark antinode
        }
    }
    println(inputArray.joinToString("\n") { it.joinToString(" ") })
}

fun findAntinodesAlongLine(first: Pair<Int, Int>, second: Pair<Int, Int>, grid: Array<CharArray>): List<Pair<Int, Int>> {
    val (x1, y1) = first
    val (x2, y2) = second

    val antinodes = mutableListOf<Pair<Int, Int>>()

    antinodes.add(first)
    antinodes.add(second)

    for (x in grid.indices) {
        for (y in grid[0].indices) {
            if ((x - x1) * (y2 - y1) == (y - y1) * (x2 - x1)) {
                antinodes.add(Pair(x, y))
            }
        }
    }

    return antinodes
}







