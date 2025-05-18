package com.example.librarymanager.di

import com.example.librarymanager.presentation.LibraryViewModel
import com.example.librarymanager.domain.usecase.GetLibraryItemsUseCase
import dagger.Module
import dagger.Provides

@Module
class LibraryModule {
    @Provides
    @LibraryScope
    fun provideLibraryViewModel(
        getLibraryItemsUseCase: GetLibraryItemsUseCase
    ): LibraryViewModel = LibraryViewModel(getLibraryItemsUseCase)
}