package com.example.groceryshoppinglist.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.groceryshoppinglist.data.local.config.ShoppingItemDatabase
import com.example.groceryshoppinglist.data.local.model.ShoppingItem
import com.example.groceryshoppinglist.shared.Constants.NAMED_MODULE
import com.example.groceryshoppinglist.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Inject
    @Named(NAMED_MODULE)
    lateinit var database: ShoppingItemDatabase
    private lateinit var sut: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()
        sut = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun should_insert_ShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            id = 1,
            name = "any_name",
            amount = 1,
            price = 1F,
            imageUrl = "any_url"
        )
        sut.insertShoppingItem(shoppingItem)
        val allShoppingItems = sut.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun should_delete_ShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            id = 1,
            name = "any_name",
            amount = 1,
            price = 1F,
            imageUrl = "any_url"
        )
        sut.insertShoppingItem(shoppingItem)
        sut.deleteShoppingItem(shoppingItem)
        val allShoppingItems = sut.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }
}