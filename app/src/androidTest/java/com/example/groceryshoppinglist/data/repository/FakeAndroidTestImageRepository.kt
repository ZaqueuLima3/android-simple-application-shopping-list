package com.example.groceryshoppinglist.data.repository

import com.example.groceryshoppinglist.domain.model.Image
import com.example.groceryshoppinglist.shared.Resource

class FakeAndroidTestImageRepository: ImageRepository {
    private val images = mutableListOf<Image>()

    override suspend fun searchForImage(imageQuery: String): Resource<List<Image>> {
        return Resource.success(images)
    }

}