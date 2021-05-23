package com.example.groceryshoppinglist.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ShoppingItem.SHOPPING_ITEM_TABLE_NAME)
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String,
    var amount: Int,
    var price: Float,
    var imageUrl: String
) {
    companion object {
        const val SHOPPING_ITEM_TABLE_NAME = "shopping_item"
    }
}
