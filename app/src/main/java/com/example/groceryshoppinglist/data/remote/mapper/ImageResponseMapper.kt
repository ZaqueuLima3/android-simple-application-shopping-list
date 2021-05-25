package com.example.groceryshoppinglist.data.remote.mapper

import com.example.groceryshoppinglist.data.remote.model.ImageResponse
import com.example.groceryshoppinglist.domain.model.Image

internal fun ImageResponse.toImageList(): List<Image> = this.hits.map { image ->
    Image(
        id = image.id,
        previewURL = image.previewURL
    )
}
