package com.example.groceryshoppinglist.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.groceryshoppinglist.MainCoroutineRule
import com.example.groceryshoppinglist.data.repository.FakeShoppingItemRepository
import com.example.groceryshoppinglist.shared.Status
import com.example.groceryshoppinglist.shared.Constants
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

    @Test
    fun `should return an error when try to insert an item with too long name`() = runBlockingTest {
        val nameWithLengthGreaterThanAllowed = buildString {
            (1..Constants.MAX_NAME_LENGTH + 1).forEach { _ -> append(1) }
        }
        val params =
            InsertShoppingItem.Params(nameWithLengthGreaterThanAllowed, "1", "3.0", "imageUrl")
        val result = sut.execute(params)
        assertThat(result.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `should return an error when try to insert an item with too long price`() =
        runBlockingTest {
            val priceWithLengthGreaterThanAllowed = buildString {
                (1..Constants.MAX_PRICE_LENGTH + 1).forEach { _ -> append(1) }
            }
            val params =
                InsertShoppingItem.Params(
                    "name",
                    "1",
                    priceWithLengthGreaterThanAllowed,
                    "imageUrl"
                )
            val result = sut.execute(params)
            assertThat(result.status).isEqualTo(Status.ERROR)
        }

    @Test
    fun `should return an error when try to insert an item with too high amount`() =
        runBlockingTest {
            val params = InsertShoppingItem.Params("name", "999999999999999", "3.0", "imageUrl")
            val result = sut.execute(params)
            assertThat(result.status).isEqualTo(Status.ERROR)
        }

    @Test
    fun `should return success when insert an item with valid input`() = runBlockingTest {
        val params = InsertShoppingItem.Params("name", "5", "3.0", "imageUrl")
        val result = sut.execute(params)
        assertThat(result.status).isEqualTo(Status.SUCCESS)
    }
}