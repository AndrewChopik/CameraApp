package com.andrew.chopik.camera

import android.app.Application
import com.andrew.chopik.camera.di.cameraModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(cameraModule))
    }
}