package com.example.groceryshoppinglist.data.repository

import com.example.groceryshoppinglist.domain.model.Image
import com.example.groceryshoppinglist.shared.Resource

interface ImageRepository {
    suspend fun searchForImage(imageQuery: String): Resource<List<Image>>
}