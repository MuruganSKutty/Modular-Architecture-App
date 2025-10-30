package com.example.modulararchitectureapp

import android.app.Application
import com.core.network.di.networkModule
import com.example.modulararchitectureapp.di.appModule
import com.features.user.di.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule, userModule, networkModule)
        }
    }
}