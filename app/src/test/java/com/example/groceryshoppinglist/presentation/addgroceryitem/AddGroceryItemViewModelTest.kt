package com.example.groceryshoppinglist.presentation.addgroceryitem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.groceryshoppinglist.MainCoroutineRule
import com.example.groceryshoppinglist.data.repository.FakeShoppingItemRepository
import com.example.groceryshoppinglist.domain.usecases.InsertShoppingItemUseCase
import com.example.groceryshoppinglist.shared.Status
import com.example.groceryshoppinglist.util.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddGroceryItemViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var sut: AddGroceryItemViewModel
    private lateinit var insertShoppingItemUseCase: InsertShoppingItemUseCase

    @Before
    fun setup() {
        insertShoppingItemUseCase = InsertShoppingItemUseCase(FakeShoppingItemRepository())
        sut = AddGroceryItemViewModel(insertShoppingItemUseCase)
    }

    @Test
    fun `should return an error when try to insert an item with empty field`() = runBlockingTest {
        sut.insertGroceryItem("name", "", "3.0")
        val result = sut.insertGroceryItem.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `should return success when insert an item with valid input`() = runBlockingTest {
        sut.insertGroceryItem("name", "5", "3.0")
        val result = sut.insertGroceryItem.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `should cleanup the image when insert an item with valid input`() = runBlockingTest {
        sut.insertGroceryItem("name", "5", "3.0")
        val result = sut.currentImage.getOrAwaitValueTest()
        assertThat(result).isEmpty()
    }

    @Test
    fun `should set an image`() {
        val expected = "https://anydomino.com/someimage.jpg"
        sut.setCurrentImageUrl(expected)
        val result = sut.currentImage.getOrAwaitValueTest()
        assertThat(result).isEqualTo(expected)
    }
}
