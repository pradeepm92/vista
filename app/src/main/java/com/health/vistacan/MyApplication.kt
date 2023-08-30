package com.health.vistacan

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object {
        lateinit var mInstance: MyApplication
        fun getContext(): Context {
            return mInstance.applicationContext
        }
    }
}