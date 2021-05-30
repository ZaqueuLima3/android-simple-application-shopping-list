package com.example.groceryshoppinglist.presentation.addgroceryitem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.groceryshoppinglist.R
import com.example.groceryshoppinglist.data.repository.FakeAndroidTestShoppingItemRepository
import com.example.groceryshoppinglist.domain.usecases.InsertShoppingItemUseCase
import com.example.groceryshoppinglist.launchFragmentInHiltContainer
import com.example.groceryshoppinglist.shared.Status
import com.example.groceryshoppinglist.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddGroceryItemFragmentTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)
    private lateinit var testViewModel: AddGroceryItemViewModel
    private lateinit var insertShoppingItemUseCase: InsertShoppingItemUseCase

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        insertShoppingItemUseCase =
            InsertShoppingItemUseCase(FakeAndroidTestShoppingItemRepository())
        testViewModel = AddGroceryItemViewModel(insertShoppingItemUseCase)
    }

    @Test
    fun shouldInsertAnItemIntoDbWhenClickOnButtonToAddNewItem() {
        launchFragmentInHiltContainer<AddGroceryItemFragment> {
            addGroceryItemViewModel = testViewModel
        }
        onView(withId(R.id.add_grocery_name)).perform(replaceText("Grocery item"))
        onView(withId(R.id.add_grocery_amount)).perform(replaceText("5"))
        onView(withId(R.id.add_grocery_price)).perform(replaceText("5.5"))
        onView(withId(R.id.add_grocery_to_list_btn)).perform(click())
        val status =
            testViewModel.insertGroceryItem.getOrAwaitValue().getContentIfNotHandled()?.status
        assertThat(status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun shouldReturnAndErrorIfAnyFieldIsMissing() {
        launchFragmentInHiltContainer<AddGroceryItemFragment> {
            addGroceryItemViewModel = testViewModel
        }
        onView(withId(R.id.add_grocery_name)).perform(replaceText("Grocery item"))
        onView(withId(R.id.add_grocery_amount)).perform(replaceText("5"))
        onView(withId(R.id.add_grocery_to_list_btn)).perform(click())
        val status =
            testViewModel.insertGroceryItem.getOrAwaitValue().getContentIfNotHandled()?.status
        assertThat(status).isEqualTo(Status.ERROR)
    }

    @Test
    fun shouldPopBackStackWhenPressedBackButton() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddGroceryItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        pressBack()
        verify(navController).popBackStack()
    }
}
