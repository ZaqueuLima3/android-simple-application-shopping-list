package com.example.groceryshoppinglist.data.remote.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("hits")
    val hits: List<Image>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
) {
    data class Image(
        @SerializedName("id")
        val id: Int,
        @SerializedName("previewURL")
        val previewURL: String,
    )
}
