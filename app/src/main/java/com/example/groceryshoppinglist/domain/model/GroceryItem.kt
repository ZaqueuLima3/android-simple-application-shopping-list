package com.example.groceryshoppinglist.domain.model

data class GroceryItem(
    val id: Int? = null,
    var name: String,
    var amount: Int,
    var price: Float,
    var imageUrl: String
)
