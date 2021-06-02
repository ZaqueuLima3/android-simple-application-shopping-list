package com.example.groceryshoppinglist.presentation.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.groceryshoppinglist.presentation.addgroceryitem.AddGroceryItemFragment
import com.example.groceryshoppinglist.presentation.imagesearch.ImageSearchAdapter
import com.example.groceryshoppinglist.presentation.imagesearch.ImageSearchFragment
import javax.inject.Inject

class GroceryFragmentFactory @Inject constructor(
    private val imageSearchAdapter: ImageSearchAdapter,
    private val glide: RequestManager
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImageSearchFragment::class.java.name -> ImageSearchFragment(imageSearchAdapter)
            AddGroceryItemFragment::class.java.name -> AddGroceryItemFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}
