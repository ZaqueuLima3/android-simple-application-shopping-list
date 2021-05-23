package com.example.groceryshoppinglist.di

import android.content.Context
import androidx.room.Room
import com.example.groceryshoppinglist.data.local.config.ShoppingItemDatabase
import com.example.groceryshoppinglist.shared.Constants.NAMED_MODULE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Named(NAMED_MODULE)
    fun provideInMemoryDb(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(context, ShoppingItemDatabase::class.java)
        .allowMainThreadQueries()
        .build()
}
