package com.example.groceryshoppinglist.data.remote.api

import com.example.groceryshoppinglist.BuildConfig
import com.example.groceryshoppinglist.data.remote.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {
    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}