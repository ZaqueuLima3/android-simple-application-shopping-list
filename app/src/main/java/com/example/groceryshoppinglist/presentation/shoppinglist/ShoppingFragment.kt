package com.example.groceryshoppinglist.presentation.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.groceryshoppinglist.R
import com.example.groceryshoppinglist.databinding.FragmentShoppingBinding

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {
    private lateinit var binding: FragmentShoppingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingBinding.inflate(layoutInflater)
        binding.btnAddNewItem.setOnClickListener { navigateToAddGroceryItem() }
        return binding.root
    }
}
