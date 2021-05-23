package com.example.groceryshoppinglist.presentation.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.groceryshoppinglist.R
import com.example.groceryshoppinglist.databinding.FragmentShoppingBinding

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {
    private lateinit var binding: FragmentShoppingBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.setContentView(
            requireActivity(),
            R.layout.fragment_shopping
        ) as FragmentShoppingBinding
    }
}