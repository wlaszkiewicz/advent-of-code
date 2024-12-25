package com.example.advent.day6

import com.example.advent.day1.readFile

fun main() {
    val input: MutableList<String> =
        readFile("app/src/main/java/com/example/advent/day6/day6.txt").lines().toMutableList()

    println(part1(input))

    //You need to get the guard stuck in a loop by adding a single new obstruction. How many different positions could you choose for this obstruction?

    println(part2(input))
}

private fun part1(input: MutableList<String>): String {

    val startingPosition = findStartingPosition(input)

    var currentY = startingPosition.first
    var currentX = startingPosition.second
    
    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(Pair(currentY, currentX))
    
    while (true) {
        if (outOfGrid(currentY, input, currentX)) {
            break
        }

        if (noObstacle(input[currentY][currentX], input, currentY, currentX)) {
            val pair = move(input, currentY, currentX)
            currentX = pair.first
            currentY = pair.second

        } else {
            turn(input, currentY, currentX)
        }

        if (!visited.contains(Pair(currentY, currentX))) {
            visited.add(Pair(currentY, currentX))
        }
    }
    return  "The number of spaces visited is: ${visited.size}"
}

private fun outOfGrid(currentY: Int, input: MutableList<String>, currentX: Int) =
    currentY <= 0 || currentY >= (input.size - 1) || currentX <= 0 || currentX >= (input[currentY].length - 1)

private fun turn(input: MutableList<String>, currentY: Int, currentX: Int) {
    val charArray = input[currentY].toCharArray()
    when (input[currentY][currentX]) {
        '^' -> charArray[currentX] = '>'
        'v' -> charArray[currentX] = '<'
        '<' -> charArray[currentX] = '^'
        '>' -> charArray[currentX] = 'v'
    }
    input[currentY] = String(charArray)
}

private fun move(input: MutableList<String>, currentY: Int, currentX: Int): Pair<Int, Int> {
    var currentY1 = currentY
    var currentX1 = currentX
    when (input[currentY1][currentX1]) {
        '^' -> {
            currentY1 -= 1
            changeSign(input, currentY1, currentX1, '^')
        }

        'v' -> {
            currentY1 += 1
            changeSign(input, currentY1, currentX1, 'v')
        }

        '<' -> {
            currentX1 -= 1
            changeSign(input, currentY1, currentX1, '<')
        }

        '>' -> {
            currentX1 += 1
            changeSign(input, currentY1, currentX1, '>')
        }
    }
    return Pair(currentX1, currentY1)
}

fun changeSign(input: MutableList<String>, currentY: Int, currentX: Int, c: Char) {
    val charArray = input[currentY].toCharArray()
    charArray[currentX] = c
    input[currentY] = String(charArray)
}

fun noObstacle(c: Char, input: List<String>, y: Int, x: Int): Boolean {
    return when (c) {
        '^' -> y - 1 >= 0 && input[y - 1][x] != '#'
        'v' -> y + 1 < input.size && input[y + 1][x] != '#'
        '<' -> x - 1 >= 0 && input[y][x - 1] != '#'
        '>' -> x + 1 < input[y].length && input[y][x + 1] != '#'
        else -> true
    }
}

fun findStartingPosition(input: List<String>): Pair<Int, Int> {
    for (i in input.indices) {
        for (j in input[i].indices) {
            if (input[i][j] in "^v<>") {
                return Pair(i, j) // i is the y coordinate, j is the x coordinate
            }
        }
    }
    return Pair(-1, -1)
}

fun part2(input: MutableList<String>): String {
    val startingPosition = findStartingPosition(input)

    return  "chuj"
}