package com.example.groceryshoppinglist.presentation.imagesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.groceryshoppinglist.R
import com.example.groceryshoppinglist.databinding.FragmentImageSearchBinding
import com.example.groceryshoppinglist.domain.model.Image
import com.example.groceryshoppinglist.presentation.addgroceryitem.AddGroceryItemViewModel
import com.example.groceryshoppinglist.shared.Constants.SEARCH_TIME_DELAY
import com.example.groceryshoppinglist.shared.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageSearchFragment @Inject constructor(
    val imageSearchAdapter: ImageSearchAdapter
) : Fragment(R.layout.fragment_image_search) {
    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ImageSearchViewModel
    lateinit var addGroceryItemViewModel: AddGroceryItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ImageSearchViewModel::class.java)
        addGroceryItemViewModel =
            ViewModelProvider(requireActivity()).get(AddGroceryItemViewModel::class.java)
        setupRecyclerview()
        bindListeners()
        bindObservers()
    }

    private fun setupRecyclerview() {
        binding.imageSearchList.apply {
            adapter = imageSearchAdapter
        }
    }

    private fun bindListeners() {
        binding.imageSearchQuery.addTextChangedListener { editable ->
            searchImage(editable.toString())
        }
        imageSearchAdapter.setOnImageClickListener {
            setCurrentImageAndPopBackStack(it)
        }
    }

    private fun bindObservers() {
        viewModel.images.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> handleImageSuccess(result.data)
                    Status.LOADING -> handleImageLoading()
                    Status.ERROR -> handleImageError(result.message)
                }
            }
        })
    }

    private fun searchImage(query: String) {
        var job: Job? = null
        if (query.isNotEmpty()) {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_TIME_DELAY)
                viewModel.searchImage(query)
            }
        }
    }

    private fun setCurrentImageAndPopBackStack(url: String) {
        addGroceryItemViewModel.setCurrentImageUrl(url)
        findNavController().popBackStack()
    }

    private fun handleImageSuccess(images: List<Image>?) {
        imageSearchAdapter.images = images.orEmpty()
        binding.imageSearchProgress.visibility = View.GONE
    }

    private fun handleImageLoading() {
        binding.imageSearchProgress.visibility = View.VISIBLE
    }

    private fun handleImageError(message: String?) {
        message?.let { showSnackBar(it) }
    }

    private fun showSnackBar(message: String) {
        val view = activity?.findViewById(R.id.root_layout) as ConstraintLayout
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}
