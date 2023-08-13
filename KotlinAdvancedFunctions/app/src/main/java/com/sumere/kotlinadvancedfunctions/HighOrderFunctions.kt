package com.sumere.kotlinadvancedfunctions

fun main(){
    val myNumList = listOf<Int>(1,3,5,7,9,11,13,15)
    //Filter
    val newNumList = myNumList.filter {num ->
        num < 6
    }
    for(num in newNumList) {
        println(num)
    }
    val squaredNumbers = myNumList.map { num -> num * num }
    for(num in squaredNumbers) {
        println(num)
    }
    val filterMapCombined = myNumList.filter { it < 6 }.map { it * it }
    for(num in filterMapCombined) {
        println(num)
    }
    val musicians = listOf<Musician>(
        Musician("James",60,"Guitar"),
        Musician("Lars",55,"Drum"),
        Musician("Kirk",50,"Guitar")
    )
    val ageRestrictedMusicians = musicians.filter { it.age < 60 }.map { it.instrument }
    for(instrument in ageRestrictedMusicians){
        println(instrument)
    }

}

data class Musician(val name: String, val age: Int,val instrument: String)