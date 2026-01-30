package com.example.advent.day5
import com.example.advent.day1.readFile

fun main() {
    val input = readFile("app/src/main/java/com/example/advent/day5/day5.txt")
    val rules = getPageRules(input)
    val updates = getUpdates(rules, input)

    println(part1(rules, updates))
    println(part2(rules, updates))
}

fun part1(rules: MutableList<List<Int>>, updates: MutableList<List<Int>>): String {
    val listOfCorrectUpdates = mutableListOf<List<Int>>()
    for (update in updates) {
        var rightOrder = true
        for (page in update) {
            for (rule in rules) {
                if (update.contains(rule[0]) && update.contains(rule[1])) { // if the update contains both pages from the rule
                         if (update.indexOf(rule[0]) > update.indexOf(rule[1])) { // if the first page is after the second page
                            rightOrder = false
                            break
                        }
                }
            }
        }
        if (rightOrder) {
            listOfCorrectUpdates.add(update)
        }
    }
    val sum = sumMiddles(listOfCorrectUpdates)
    return "The sum of the middle pages of the correct updates is: $sum"
}

fun sumMiddles(listOfCorrectUpdates: MutableList<List<Int>>): Int {
    var sum = 0
    for (update in listOfCorrectUpdates) {
        sum += update[update.size / 2] // add the middle page to the sum
    }
    return sum
}

private fun getUpdates(rules: MutableList<List<Int>>, input: String): MutableList<List<Int>> {
    val list2 = mutableListOf<List<Int>>()
    for (i in (rules.size + 1) until input.lines().size) {
        val lineList = input.lines()[i].split(",").map { it.toInt() }.toList()
        list2.add(lineList)
    }
    return list2
}

private fun getPageRules(input: String): MutableList<List<Int>> {
    val list1 = mutableListOf<List<Int>>()
    for (line in input.lines()) {
        if (line.isBlank()) break
        val lineList = line.split("|").map { it.toInt() }.toList()
        list1.add(lineList)
    }
    return list1
}

fun part2(rules: MutableList<List<Int>>, updates: MutableList<List<Int>>): String {
    val listOfIncorrectUpdates = mutableListOf<List<Int>>()
    for (update in updates) {
        var rightOrder = true
        for (page in update) {
            for (rule in rules) {
                if (update.contains(rule[0]) && update.contains(rule[1])) { // if the update contains both pages from the rule
                    if (update.indexOf(rule[0]) > update.indexOf(rule[1])) { // if the first page is after the second page
                        rightOrder = false
                        break
                    }
                }
            }
        }
        if (!rightOrder) {
            listOfIncorrectUpdates.add(update)
        }
    }
    val listOfCorrectedUpdates = correctUpdates(listOfIncorrectUpdates, rules)

    val sum = sumMiddles(listOfCorrectedUpdates.map { it.toList() }.toMutableList())
    return "The sum of the middle pages of the corrected updates is: $sum"
}

fun correctUpdates(listOfIncorrectUpdates: MutableList<List<Int>>, rules: MutableList<List<Int>>): MutableList<MutableList<Int>> {
    val listOfCorrectedUpdates = listOfIncorrectUpdates.map { it.toMutableList() }.toMutableList()
     for (update in listOfCorrectedUpdates) {
            for (page in update) {
                for (rule in rules) {
                    if (update.contains(rule[0]) && update.contains(rule[1])) {
                        if (update.indexOf(rule[0]) > update.indexOf(rule[1])) { // if the first page is after the second page
                            val temp = update[update.indexOf(rule[0])]
                            update[update.indexOf(rule[0])] = update[update.indexOf(rule[1])]
                            update[update.indexOf(rule[1])] = temp
                        }
                    }
                }
            }
    }
    return listOfCorrectedUpdates
}