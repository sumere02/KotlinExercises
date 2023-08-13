package com.sumere.kmmmovieapp.util

import com.sumere.kmmmovieapp.di.getSharedModules
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(getSharedModules())
    }
}