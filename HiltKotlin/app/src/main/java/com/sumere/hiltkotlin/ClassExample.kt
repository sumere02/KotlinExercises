package com.sumere.hiltkotlin

import javax.inject.Inject

class ClassExample @Inject constructor(private val myInterfaceImplementer: MyInterface) {
    fun myFunction(): String {
        return "Working ${myInterfaceImplementer.myPrintFunction()}"
    }
}