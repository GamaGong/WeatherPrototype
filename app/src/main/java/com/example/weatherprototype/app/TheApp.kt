package com.example.weatherprototype.app

import android.app.Application
import com.example.weatherprototype.di.KoinDi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TheApp : Application() {
    @FlowPreview
    @ExperimentalCoroutinesApi
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