package com.example.groceryshoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.groceryshoppinglist.databinding.ActivityMainBinding
import com.example.groceryshoppinglist.presentation.factory.GroceryFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var fragmentFactory: GroceryFragmentFactory
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
