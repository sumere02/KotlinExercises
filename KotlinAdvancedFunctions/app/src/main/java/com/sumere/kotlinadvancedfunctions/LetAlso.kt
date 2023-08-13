package com.sumere.kotlinadvancedfunctions


private var myInt: Int? = null

fun main(){
    //Let
    myInt?.let {
        val num = it + 1
    }
    val num = myInt?.let {
        it + 1
    }?: 0

    //Also
    val emir = Person("Emir",21)
    val recep = Person("Recep",56)
    val melek = Person("Melek",53)
    val people = listOf<Person>(emir,recep,melek)
    people.filter { it.age > 22 }.also {
        for(person in it){
            println(person.name)
        }
    }
}

data class Person(var name:String,var age: Int)