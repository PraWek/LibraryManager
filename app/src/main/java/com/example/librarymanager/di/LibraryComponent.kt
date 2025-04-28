package com.example.librarymanager.di

import com.example.librarymanager.MainActivity
import dagger.Component

@LibraryScope
@Component(dependencies = [AppComponent::class], modules = [LibraryModule::class])
interface LibraryComponent {
    fun inject(activity: MainActivity)
}
