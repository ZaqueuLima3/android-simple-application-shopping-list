package com.example.groceryshoppinglist.presentation.addgroceryitem

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryshoppinglist.domain.model.GroceryItem
import com.example.groceryshoppinglist.domain.model.Image
import com.example.groceryshoppinglist.domain.usecases.InsertShoppingItemUseCase
import com.example.groceryshoppinglist.shared.Event
import com.example.groceryshoppinglist.shared.Resource

class AddGroceryItemViewModel @ViewModelInject constructor(
    private val insertShoppingItemUseCase: InsertShoppingItemUseCase
) : ViewModel() {
    private val _currentImage = MutableLiveData<String>()
    val currentImage: LiveData<String>
        get() = _currentImage

    private val _insertGroceryItemStatus = MutableLiveData<Event<Resource<GroceryItem>>>()
    val insertGroceryItem: LiveData<Event<Resource<GroceryItem>>>
        get() = _insertGroceryItemStatus

    fun setCurrentImageUrl(url: String) {
        _currentImage.postValue(url)
    }

    suspend fun insertGroceryItem(name: String, amount: String, price: String) {
        val groceryItemResource = insertShoppingItemUseCase.execute(
            InsertShoppingItemUseCase.Params(
                name,
                amount,
                price,
                _currentImage.value ?: ""
            )
        )
        setCurrentImageUrl("")
        _insertGroceryItemStatus.postValue(Event(groceryItemResource))
    }
}
