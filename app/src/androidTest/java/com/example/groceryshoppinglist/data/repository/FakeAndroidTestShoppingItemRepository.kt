package com.example.groceryshoppinglist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groceryshoppinglist.data.local.model.ShoppingItem

class FakeAndroidTestShoppingItemRepository: ShoppingItemRepository {
    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItem = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return observableShoppingItem
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    private fun refreshLiveData() {
        observableShoppingItem.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }
}