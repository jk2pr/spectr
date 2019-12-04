package com.jk.spectr

import android.app.Application
import com.jk.spectr.di.components.AppComponent
import com.jk.spectr.di.components.DaggerAppComponent
import com.jk.spectr.di.modules.NetworkModule

class SpectrApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()


    }
}