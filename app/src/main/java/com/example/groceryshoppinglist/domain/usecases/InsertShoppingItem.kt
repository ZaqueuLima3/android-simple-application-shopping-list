package com.example.groceryshoppinglist.domain.usecases

import com.example.groceryshoppinglist.data.local.model.ShoppingItem
import com.example.groceryshoppinglist.data.repository.ShoppingItemRepository
import com.example.groceryshoppinglist.shared.Constants
import com.example.groceryshoppinglist.shared.Resource
import timber.log.Timber

class InsertShoppingItem(
    private val shoppingItemRepository: ShoppingItemRepository
) : UseCase<InsertShoppingItem.Params, Resource<ShoppingItem>> {
    override suspend fun execute(params: Params?): Resource<ShoppingItem> {
        return try {
            requireNotNull(params) { "Params must not be null." }
            if (params.name.isBlank() || params.amountString.isBlank() || params.priceString.isBlank()) {
                return  Resource.error(ERROR_EMPTY_FIELD, null)
            }
            return Resource.error(ERROR_EMPTY_FIELD, null)
        } catch (e: Exception) {
            Resource.error(e.message.toString(), null)
        }
    }

    data class Params(
        val name: String,
        val amountString: String,
        val priceString: String,
        val imageUrl: String
    )

    companion object {
        const val ERROR_EMPTY_FIELD = "The fields must not be empty"
        const val ERROR_EXCEED_NAME_LENGTH = "The name of the item must not exceed ${Constants.MAX_NAME_LENGTH}"
        const val ERROR_EXCEED_PRICE_LENGTH = "The price of the item must not exceed ${Constants.MAX_PRICE_LENGTH}"
        const val ERROR_INVALID_AMOUNT = "Please enter a valid amount"
    }
}