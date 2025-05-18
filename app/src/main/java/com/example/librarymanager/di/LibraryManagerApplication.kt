package com.example.librarymanager.di

class LibraryManagerApplication {
}package com.example.librarymanager

import android.app.Application
import com.example.librarymanager.di.AppComponent
import com.example.librarymanager.di.DaggerAppComponent

class LibraryManagerApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }
}