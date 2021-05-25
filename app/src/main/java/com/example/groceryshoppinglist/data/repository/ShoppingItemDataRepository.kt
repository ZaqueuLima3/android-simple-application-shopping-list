package com.example.groceryshoppinglist.data.repository

import androidx.lifecycle.LiveData
import com.example.groceryshoppinglist.data.local.dao.ShoppingDao
import com.example.groceryshoppinglist.data.local.model.ShoppingItem

class ShoppingItemDataRepository(
    private val shoppingDao: ShoppingDao
) : ShoppingItemRepository  {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }
}
