package com.example.advent.day4

import com.example.advent.day1.readFile
fun main() {
    val input = readFile("app/src/main/java/com/example/advent/day4/day4.txt")

    println(part1(input))

    println(part2(input))
}

fun part1(input: String): String {
    val patternXMAS = "XMAS"
    val wordsXMAS = checkAllDirections(input, patternXMAS.toRegex())
    val wordsSMAX = checkAllDirections(input, patternXMAS.reversed().toRegex())

    val allWords = wordsXMAS + wordsSMAX
    return "The number of valid XMAS words is: $allWords"
}

fun part2(input: String): String {
    val patternMAS = "MAS"
    val lines = input.lines()
    val crossMAS = checkOneSide(lines, patternMAS.toRegex(), patternMAS.reversed().toRegex())

    return "The number of valid MAS crosses is: $crossMAS"

}

private fun checkOneSide(lines: List<String>, patternMAS: Regex, patternSAM: Regex): Int {
    var count = 0
    for (j in 0 until (lines.size - 2)) { // line num
        for (i in 0 until (lines[0].length - 2)) {  // char num
            val word =
                lines[j][i].toString() + lines[j + 1][i + 1] + lines[j + 2][i + 2]
            if (patternMAS.matches(word) || patternSAM.matches(word)) {
                count += checkOtherSide(i + 2, j, lines, patternMAS, patternSAM)
            }
        }
    }
    return count
}

fun checkOtherSide(i: Int, j: Int, lines: List<String>, patternMAS: Regex, patternSAM: Regex): Int {
    val word =
        lines[j][i].toString() + lines[j + 1][i - 1] + lines[j + 2][i - 2]
    if (patternMAS.matches(word) || patternSAM.matches(word)) {
        return 1
    }

    return 0

}

fun checkAllDirections(input: String, pattern: Regex): Int {
    return checkHorizontal(input, pattern) + checkVertical(input, pattern) + checkDiagonal(input, pattern)

}

fun checkHorizontal(input: String, pattern: Regex): Int {
    var count = 0
    val lines = input.lines()
    for (line in lines) {
        count += pattern.findAll(line).count()
    }
    println("Horizontal: $count")
    return count

}

fun checkVertical(input: String, pattern: Regex): Int {
    var count = 0
    val lines = input.lines()
    for (i in lines[0].indices) {
        var lineVertical = ""
        for (line in lines) {
            lineVertical += line[i]
        }
        count += pattern.findAll(lineVertical).count()
    }
    println("Vertical: $count")
    return count
}

fun checkDiagonal(input: String, pattern: Regex): Int {
    var count = 0
    val lines = input.lines()
    val n = lines.size
    val m = lines[0].length

    //diagonals from top-left to bottom-right
    for (k in 0 until n + m - 1) {
        var lineDiagonal = ""
        for (j in 0..k) {
            val i = k - j
            if (i in 0 until n && j in 0 until m) {
                lineDiagonal += lines[i][j]
            }
        }
        count += pattern.findAll(lineDiagonal).count()
    }

    //diagonals from top-right to bottom-left
    for (k in 1 - m until n) {
        var lineDiagonal = ""
        for (j in 0 until m) {
            val i = k + j
            if (i in 0 until n) {
                lineDiagonal += lines[i][j]
            }
        }
        count += pattern.findAll(lineDiagonal).count()
    }

    println("Diagonal: $count")

    return count
}
