package com.example.groceryshoppinglist.presentation.addgroceryitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.groceryshoppinglist.R
import com.example.groceryshoppinglist.databinding.FragmentAddGroceryItemBinding
import com.example.groceryshoppinglist.shared.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddGroceryItemFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_add_grocery_item) {
    private var _binding: FragmentAddGroceryItemBinding? = null
    private val binding get() = _binding!!
    lateinit var addGroceryItemViewModel: AddGroceryItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGroceryItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addGroceryItemViewModel =
            ViewModelProvider(requireActivity()).get(AddGroceryItemViewModel::class.java)
        bindObservers()
        bindListeners()
        setOnBackPressed()
    }

    private fun bindObservers() {
        addGroceryItemViewModel.insertGroceryItem.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> handleStatusSuccess()
                    Status.ERROR -> handleStatusError(result.message)
                    Status.LOADING -> handleStatusLoading()
                }
            }
        })
        addGroceryItemViewModel.currentImage.observe(viewLifecycleOwner, {
            glide.load(it)
                .into(binding.addGroceryImage)
        })
    }

    private fun bindListeners() {
        binding.addGroceryImage.setOnClickListener {
            findNavController().navigate(
                AddGroceryItemFragmentDirections.actionAddGroceryItemFragmentToImageSearchFragment()
            )
        }
        binding.addGroceryToListBtn.setOnClickListener {
            lifecycleScope.launch {
                addGroceryItemViewModel.insertGroceryItem(
                    binding.addGroceryName.text.toString(),
                    binding.addGroceryAmount.text.toString(),
                    binding.addGroceryPrice.text.toString()
                )
            }
        }
    }


    private fun setOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun handleStatusSuccess() {
        showSnackBar(getString(R.string.added_shopping_item))
        findNavController().popBackStack()
    }

    private fun handleStatusLoading() {
        TODO("Not yet implemented")
    }

    private fun handleStatusError(message: String?) {
        message?.let { showSnackBar(it) }
    }

    private fun showSnackBar(message: String) {
        val view = activity?.findViewById(R.id.root_layout) as ConstraintLayout
        Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
