package com.tuit_21019.pdpuzkurslar.app

import android.app.Application
import com.tuit_21019.pdpuzkurslar.DataBase.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }
}