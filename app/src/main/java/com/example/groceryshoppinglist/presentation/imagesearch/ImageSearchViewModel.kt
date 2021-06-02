package com.example.groceryshoppinglist.presentation.imagesearch

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groceryshoppinglist.domain.model.Image
import com.example.groceryshoppinglist.domain.usecases.SearchImageUseCase
import com.example.groceryshoppinglist.shared.Event
import com.example.groceryshoppinglist.shared.Resource
import kotlinx.coroutines.launch

class ImageSearchViewModel @ViewModelInject constructor(
    private val searchImageUseCase: SearchImageUseCase
) : ViewModel() {
    private val _images = MutableLiveData<Event<Resource<List<Image>>>>()
    val images: LiveData<Event<Resource<List<Image>>>>
        get() = _images

    fun searchImage(query: String) {
        _images.value = Event(Resource.loading())
        viewModelScope.launch {
            val response = searchImageUseCase.execute(SearchImageUseCase.Params(query))
            _images.value = Event(response)
        }
    }
}
