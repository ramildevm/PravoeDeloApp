package com.example.pravoedeloapp

import android.app.Application
import com.example.pravoedeloapp.di.AppComponent
import com.example.pravoedeloapp.di.DaggerAppComponent

class App: Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}