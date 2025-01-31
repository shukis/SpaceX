package com.spaceix

import android.app.Application
import com.spaceix.di.initKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

}