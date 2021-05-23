package com.example.groceryshoppinglist.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.groceryshoppinglist.data.local.model.ShoppingItem
import com.example.groceryshoppinglist.data.local.model.ShoppingItem.Companion.SHOPPING_ITEM_TABLE_NAME

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM $SHOPPING_ITEM_TABLE_NAME")
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>
}
