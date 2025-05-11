package com.example.librarymanager.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20,
        @Query("fields") fields: String = "items(volumeInfo(title,authors,pageCount),volumeInfo/industryIdentifiers)"
    ): BooksResponse
}

data class BooksResponse(
    val items: List<BookItem>? = null
)

data class BookItem(
    val volumeInfo: VolumeInfo? = null
)

data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    val pageCount: Int? = 0,
    val industryIdentifiers: List<IndustryIdentifier>? = null
)

data class IndustryIdentifier(
    val type: String? = null,
    val identifier: String? = null
)
