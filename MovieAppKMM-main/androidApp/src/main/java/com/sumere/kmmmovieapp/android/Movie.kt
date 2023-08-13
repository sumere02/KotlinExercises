package com.sumere.kmmmovieapp.android

import android.app.Application
import com.sumere.kmmmovieapp.android.di.appModule
import com.sumere.kmmmovieapp.di.getSharedModules
import org.koin.core.context.startKoin

class Movie: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule + getSharedModules())
        }
    }
}