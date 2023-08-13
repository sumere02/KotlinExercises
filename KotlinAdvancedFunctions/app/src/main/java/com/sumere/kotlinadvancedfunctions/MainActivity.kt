package com.sumere.kotlinadvancedfunctions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MainActivity : AppCompatActivity(), LifeCycleLogger by LifeCycleLoggerImplementation() {

    //Property delegates

    //If value is used then memory initializes
    private val myVariable by lazy{
        println("Lazy implementation")
        20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerLifeCycleOwner(this)

    }
}

interface LifeCycleLogger{
    fun registerLifeCycleOwner(owner: LifecycleOwner)
}

class LifeCycleLoggerImplementation: LifeCycleLogger, LifecycleEventObserver{
    override fun registerLifeCycleOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> println("On Resume Executed")
            Lifecycle.Event.ON_PAUSE -> println("On Pause Executed")
            else -> Unit
        }
    }

}