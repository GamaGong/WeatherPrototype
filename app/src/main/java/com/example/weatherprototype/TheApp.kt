package com.example.weatherprototype

import android.app.Application
import com.example.weatherprototype.di.KoinDi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TheApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TheApp)
            modules(
                KoinDi.koinModules
            )
        }
    }
}