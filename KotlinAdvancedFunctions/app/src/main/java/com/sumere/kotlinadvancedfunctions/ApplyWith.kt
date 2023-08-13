package com.sumere.kotlinadvancedfunctions

import android.content.Intent

fun main(){
    //Apply
    val intent = Intent()
    intent.putExtra("","")
    intent.putExtra("","")

    val intentWithApply = Intent().apply {
        putExtra("","")
        putExtra("","")
    }

    //With
    Person("barley",4).apply {
        name = "Barley"
    }.also {
        println(it.name)
    }

    val newBarley = Person("bar",4).apply {
        name = "Barley"
    }
    println(newBarley.name)

    val anotherBarley = with(Person("Basd",4)){
        name = "Barley"
    }
    val withBarley = Person("Basd",4)
    with(withBarley){
        name = "Barley"
    }
    println(withBarley.name)

}