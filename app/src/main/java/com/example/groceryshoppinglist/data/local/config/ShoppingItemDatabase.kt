package com.example.groceryshoppinglist.data.local.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.groceryshoppinglist.data.local.dao.ShoppingDao
import com.example.groceryshoppinglist.data.local.model.ShoppingItem

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao
}
