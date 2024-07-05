package com.redev.scg_test

fun main(){
    /**
     * 1. Please write a function to find the index that has the sum of left’s elements equal to the sum of
     * right’s elements .
     */
    val input1 = intArrayOf(1, 3, 5, 7, 9)
    val input2 = intArrayOf(3, 6, 8, 1, 5, 10, 1, 7)
    val input3 = intArrayOf(3, 5, 6)

    println(input1.findMiddle())
    println(input2.findMiddle())
    println(input3.findMiddle())
    println("\n----------------------------------------\n")

    /**
     * 2. Please write a function to detect that incoming string is palindrome or not
     */
    val str1 = "aka"
    val str2 = "Level"
    val str3 = "Hello"

    println(str1.isPalindrome())
    println(str2.isPalindrome())
    println(str3.isPalindrome())
    println("\n----------------------------------------\n")

    /**
     * 3. Bonus Please write a function to find triplets array that the sum of the numbers is equal to
     * zero
     * Note:
     * 1. The input array nums may contain duplicate elements, but the solution set must not contain
     * duplicate triplets.
     * 2. The function should have a time complexity better than O(N^3), where N is the number of
     * elements in the array nums.
     */
    val arr1 = intArrayOf(-1, -5, -3, 0, 1, 2, -1)
    val arr2 = intArrayOf(1, 1, 2)
    val arr3 = intArrayOf(1)

    println(arr1.findTriplets())
    println(arr2.findTriplets())
    println(arr3.findTriplets())
}


fun IntArray.findMiddle():String {
    val totalSum = this.sum()
    var leftSum = 0

    for (i in this.indices){
        val rightSum = totalSum - leftSum - this[i]

        if(rightSum == leftSum){
            return  "middle index is $i"
        }
        leftSum += this[i]
    }

    return "index not found"
}

fun String.isPalindrome(): String {
    val normalizedStr = this.lowercase()
    val len = normalizedStr.length

    for (i in 0 until len / 2) {
        if (normalizedStr[i] != normalizedStr[len - i - 1]) {
            return "$this isn't a palindrome"
        }
    }

    return "$this is a palindrome"
}

fun IntArray.findTriplets(): List<List<Int>> {
    this.sort()
    val result = mutableSetOf<List<Int>>()

    for (i in this.indices) {
        if (i > 0 && this[i] == this[i - 1]) continue // ข้ามตัวเลขที่ซ้ำกัน
        var left = i + 1
        var right = this.size - 1

        while (left < right) {
            val sum = this[i] + this[left] + this[right]

            when {
                sum == 0 -> {
                    result.add(listOf(this[i], this[left], this[right]))
                    left++
                    right--
                    while (left < right && this[left] == this[left - 1]) left++ // ข้ามตัวเลขที่ซ้ำกัน
                    while (left < right && this[right] == this[right + 1]) right-- // ข้ามตัวเลขที่ซ้ำกัน
                }
                sum < 0 -> left++
                else -> right--
            }
        }
    }

    return result.toList()
}