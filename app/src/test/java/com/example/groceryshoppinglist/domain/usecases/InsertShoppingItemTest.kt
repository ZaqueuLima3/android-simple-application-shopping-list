package com.example.groceryshoppinglist.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.groceryshoppinglist.MainCoroutineRule
import com.example.groceryshoppinglist.data.repository.FakeShoppingItemRepository
import com.example.groceryshoppinglist.shared.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class InsertShoppingItemTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var sut: InsertShoppingItem

    @Before
    fun setup() {
        sut = InsertShoppingItem(FakeShoppingItemRepository())
    }

    @Test
    fun `should return an error when try to insert an item with empty field`() = runBlockingTest {
        val params = InsertShoppingItem.Params("", "", "3.0", "imageUrl")
        val result = sut.execute(params)
        assertThat(result.status).isEqualTo(Status.ERROR)
    }
}