package com.example.librarymanager.di

import com.example.librarymanager.network.GoogleBooksApi
import com.example.librarymanager.network.NetworkUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideGoogleBooksApi(): GoogleBooksApi = NetworkUtils.booksApi
}