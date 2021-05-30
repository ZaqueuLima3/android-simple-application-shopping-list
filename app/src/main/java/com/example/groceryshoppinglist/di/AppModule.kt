package com.example.groceryshoppinglist.di

import android.content.Context
import androidx.room.Room
import com.example.groceryshoppinglist.data.local.config.ShoppingItemDatabase
import com.example.groceryshoppinglist.data.local.dao.ShoppingDao
import com.example.groceryshoppinglist.data.remote.api.PixabayAPI
import com.example.groceryshoppinglist.data.repository.ImageDataRepository
import com.example.groceryshoppinglist.data.repository.ImageRepository
import com.example.groceryshoppinglist.data.repository.ShoppingItemDataRepository
import com.example.groceryshoppinglist.data.repository.ShoppingItemRepository
import com.example.groceryshoppinglist.domain.usecases.InsertShoppingItemUseCase
import com.example.groceryshoppinglist.shared.Constants.BASE_URL
import com.example.groceryshoppinglist.shared.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideImageDataRepository(
        api: PixabayAPI
    ) = ImageDataRepository(api) as ImageRepository

    @Singleton
    @Provides
    fun provideShoppingItemDataRepository(
        dao: ShoppingDao
    ) = ShoppingItemDataRepository(dao) as ShoppingItemRepository

    @Singleton
    @Provides
    fun provideInsetShoppingItem(
        shoppingItemRepository: ShoppingItemRepository
    ) = InsertShoppingItemUseCase(shoppingItemRepository)
}
