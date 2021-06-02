package com.example.groceryshoppinglist.presentation.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.groceryshoppinglist.presentation.addgroceryitem.AddGroceryItemFragment
import com.example.groceryshoppinglist.presentation.imagesearch.ImageSearchAdapter
import com.example.groceryshoppinglist.presentation.imagesearch.ImageSearchFragment
import javax.inject.Inject

class TestGroceryFragmentFactory @Inject constructor(
    private val glide: RequestManager,
    private val imageSearchAdapter: ImageSearchAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            AddGroceryItemFragment::class.java.name -> AddGroceryItemFragment(glide)
            ImageSearchFragment::class.java.name -> ImageSearchFragment(imageSearchAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}
