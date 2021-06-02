package com.example.groceryshoppinglist.presentation.imagesearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.groceryshoppinglist.R
import com.example.groceryshoppinglist.data.repository.FakeAndroidTestImageRepository
import com.example.groceryshoppinglist.data.repository.FakeAndroidTestShoppingItemRepository
import com.example.groceryshoppinglist.data.repository.ImageRepository
import com.example.groceryshoppinglist.data.repository.ShoppingItemRepository
import com.example.groceryshoppinglist.domain.model.Image
import com.example.groceryshoppinglist.domain.usecases.InsertShoppingItemUseCase
import com.example.groceryshoppinglist.domain.usecases.SearchImageUseCase
import com.example.groceryshoppinglist.launchFragmentInHiltContainer
import com.example.groceryshoppinglist.presentation.addgroceryitem.AddGroceryItemViewModel
import com.example.groceryshoppinglist.presentation.factory.TestGroceryFragmentFactory
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
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageSearchFragmentTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: TestGroceryFragmentFactory
    private lateinit var searchImageUseCase: SearchImageUseCase
    private lateinit var insertShoppingItemUseCase: InsertShoppingItemUseCase
    private lateinit var imageRepository: ImageRepository
    private lateinit var shoppingItemRepository: ShoppingItemRepository
    private lateinit var viewModelTest: ImageSearchViewModel
    private lateinit var addGroceryItemViewModelTest: AddGroceryItemViewModel
    private lateinit var navController: NavController

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        navController = mock(NavController::class.java)
        imageRepository = FakeAndroidTestImageRepository()
        searchImageUseCase = SearchImageUseCase(imageRepository)
        viewModelTest = ImageSearchViewModel(searchImageUseCase)
        shoppingItemRepository = FakeAndroidTestShoppingItemRepository()
        insertShoppingItemUseCase = InsertShoppingItemUseCase(shoppingItemRepository)
        addGroceryItemViewModelTest = AddGroceryItemViewModel(insertShoppingItemUseCase)
    }

    @Test
    fun shouldPopBackStackWhenClickOnAnImage() {
        launchFragmentInHiltContainer<ImageSearchFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            imageSearchAdapter.images = listOf(
                Image(
                    id = 0,
                    previewURL = "any_url"
                )
            )
            viewModel = viewModelTest
        }
        onView(withId(R.id.image_search_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageSearchAdapter.ViewHolder>(
                0,
                click()
            )
        )
        verify(navController).popBackStack()
    }

    @Test
    fun shouldSetCurrentImageWhenSelectAnImage() {
        val image = Image(
            id = 0,
            previewURL = "any_url"
        )
        launchFragmentInHiltContainer<ImageSearchFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            imageSearchAdapter.images = listOf(
                image
            )
            viewModel = viewModelTest
            addGroceryItemViewModel = addGroceryItemViewModelTest
        }
        onView(withId(R.id.image_search_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageSearchAdapter.ViewHolder>(
                0,
                click()
            )
        )
        assertThat(addGroceryItemViewModelTest.currentImage.getOrAwaitValue()).isEqualTo(image.previewURL)
    }
}