package com.gmail.gabow95k.taski

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.gmail.gabow95k.taski.di.ComponentInjector
import com.gmail.gabow95k.taski.di.DaggerComponentInjector

class TaskiApplication : Application() {

    @SuppressLint("StaticFieldLeak")
    companion object {
        lateinit var context: Context
        lateinit var mInstance: TaskiApplication
        lateinit var component: ComponentInjector
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        component =
            DaggerComponentInjector.builder().application(this).build()
        mInstance = this
    }

    @Synchronized
    fun getInstance(): TaskiApplication {
        return mInstance
    }

}