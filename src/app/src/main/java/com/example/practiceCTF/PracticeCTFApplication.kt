package com.example.practiceCTF

import android.app.Application

class PracticeCTFApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BackgroundPingScheduler.start()
    }
}

