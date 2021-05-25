package com.example.groceryshoppinglist.data.repository

import com.example.groceryshoppinglist.data.remote.api.PixabayAPI
import com.example.groceryshoppinglist.data.remote.mapper.toImageList
import com.example.groceryshoppinglist.domain.model.Image
import com.example.groceryshoppinglist.shared.Resource

class ImageDataRepository(
    private val pixabayAPI: PixabayAPI
) : ImageRepository {
    override suspend fun searchForImage(imageQuery: String): Resource<List<Image>> {
        val response = pixabayAPI.searchForImage(imageQuery)
        if (!response.isSuccessful) {
            return Resource.error(ERROR_CONNECTION)
        }
        return response.body()?.let {
            return@let Resource.success(it.toImageList())
        } ?: Resource.error(UNKNOWN_ERROR)
    }

    companion object {
        const val UNKNOWN_ERROR = "An unknown error occurred"
        const val ERROR_CONNECTION = "Couldn't reach the server. Check your internet connection"
    }
}
