package com.example.advent.day9
import com.example.advent.day1.readFile
fun main() {
    val input: MutableList<Char> = readFile("app/src/main/java/com/example/advent/day9/day9.txt").toMutableList()
    //val input = "2333133121414131402".toMutableList()
    println(part1(input))

    println(part2(input))

}

fun part1(input: MutableList<Char>): String {
    val stringBlocks = getStringBlocks(input)
   // println(stringBlocks)
    moveBlocks(stringBlocks)
    //println(stringBlocks)
    val sum = fileSystemChecksum(stringBlocks)

    return "Resulting filesystem checksum is: $sum"

}

fun fileSystemChecksum(stringBlocks: MutableList<String>): Any {
    var sum: Long = 0
    for (i in stringBlocks.indices) {
        sum += stringBlocks[i].toLong() * i
    }
    return sum
}

fun moveBlocks(stringBlocks: MutableList<String>){
    for (i in stringBlocks.size - 1 downTo 0) {
        for (j in stringBlocks.indices) {
            if (stringBlocks[j] == ".") {
                stringBlocks[j] = stringBlocks[i]
                stringBlocks.removeAt(i)
                break
            }
        }
    }
}

private fun getStringBlocks(input: MutableList<Char>): MutableList<String> {
    val newList = mutableListOf<String>()
    var k = 0
    for (i in input.indices) {
        if (i % 2 == 0) {  // files
            for (j in 0 until input[i].digitToInt()) {
                newList.add(k.toString())
            }
            k++

        } else {  // free space
            for (j in 0 until input[i].digitToInt()) {
                newList.add(".")
            }
        }
    }
    return newList
}

fun part2(input: MutableList<Char>): String {
    val stringBlocks = getStringBlocks2(input)
    //println("Initial blocks: $stringBlocks")
    moveBlocks2(stringBlocks)
    //println("After movement: $stringBlocks")
    val sum = fileSystemChecksum2(stringBlocks)

    return "Resulting filesystem checksum is: $sum"
}

fun getStringBlocks2(input: MutableList<Char>): MutableList<String> {
    val newList = mutableListOf<String>()
    var blockId = 0
    for (i in input.indices) {
        if (i % 2 == 0) { // Files
            repeat(input[i].digitToInt()) {
                newList.add(blockId.toString())
            }
            blockId++
        } else { // Free space
            repeat(input[i].digitToInt()) {
                newList.add(".")
            }
        }
    }
    return newList
}

fun moveBlocks2(stringBlocks: MutableList<String>) {
    val fileIds = stringBlocks.filter { it != "." }.toSet().map { it.toInt() }.sortedDescending() // get all file ids

    for (fileId in fileIds) { // move blocks
        val startIndex = stringBlocks.indexOf(fileId.toString())
        val blockSize = getNumOfBlocksWithSameID(startIndex, stringBlocks)

        val spaceIndex = findLeftmostAvailableSpace(stringBlocks, blockSize)
        if (spaceIndex != -1 && spaceIndex < startIndex) {
            for (j in 0 until blockSize) {
                stringBlocks[spaceIndex + j] = fileId.toString()
            }
            for (j in 0 until blockSize) {
                stringBlocks[startIndex + j] = "."
            }
        }
    }
}

fun getNumOfBlocksWithSameID(startIndex: Int, stringBlocks: MutableList<String>): Int {
    val blockId = stringBlocks[startIndex]
    var count = 0
    for (i in startIndex until stringBlocks.size) {
        if (stringBlocks[i] == blockId) {
            count++
        } else {
            break
        }
    }
    return count
}

fun findLeftmostAvailableSpace(stringBlocks: MutableList<String>, blockSize: Int): Int {
    for (i in 0..stringBlocks.size - blockSize) { // check if block can fit
        if (canFitBlock(stringBlocks, blockSize, i)) {
            return i
        }
    }
    return -1
}

fun canFitBlock(stringBlocks: MutableList<String>, blockSize: Int, startIndex: Int): Boolean {
    for (i in startIndex until startIndex + blockSize) {
        if (i >= stringBlocks.size || stringBlocks[i] != ".") { // block cannot fit
            return false
        }
    }
    return true
}

fun fileSystemChecksum2(stringBlocks: MutableList<String>): Long {
    var sum: Long = 0
    for (i in stringBlocks.indices) {
        if (stringBlocks[i] != ".") {
            sum += stringBlocks[i].toLong() * i
        }
    }
    return sum
}



