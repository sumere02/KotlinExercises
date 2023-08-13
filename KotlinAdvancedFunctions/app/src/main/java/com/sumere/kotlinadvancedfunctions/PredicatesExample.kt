package com.sumere.kotlinadvancedfunctions

fun main(){
    val myNumList = listOf<Int>(1,3,5,7,9,11,13,15)

    val allCheck: Boolean = myNumList.all { it > 5 }

    val anyCheck: Boolean = myNumList.any { it > 5 }

    val totalCount: Int = myNumList.count{it > 5 }

    val findNum = myNumList.find { it > 5 }

    val findLastNum= myNumList.findLast { it > 5 }
}