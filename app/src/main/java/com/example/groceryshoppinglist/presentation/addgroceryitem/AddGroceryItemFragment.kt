package com.example.groceryshoppinglist.presentation.addgroceryitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.groceryshoppinglist.R
import com.example.groceryshoppinglist.databinding.FragmentAddGroceryItemBinding

class AddGroceryItemFragment: Fragment(R.layout.fragment_add_grocery_item){
    private lateinit var binding: FragmentAddGroceryItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddGroceryItemBinding.inflate(layoutInflater)
        return binding.root
    }

}
