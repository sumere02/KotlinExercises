package com.sumere.hiltkotlin

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


interface MyInterface {
    fun myPrintFunction(): String
}

@InstallIn(ActivityComponent::class)
@Module
abstract class MyModule{
    @Binds
    abstract fun bindingFunction(myImplementer: InterfaceImplementer): MyInterface
}

