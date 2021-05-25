package com.example.groceryshoppinglist.data.repository

import androidx.lifecycle.LiveData
import com.example.groceryshoppinglist.data.local.model.ShoppingItem

interface ShoppingItemRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>
}
