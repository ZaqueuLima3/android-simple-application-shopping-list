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
            if (params.name.length > Constants.MAX_NAME_LENGTH) {
                return Resource.error(ERROR_EXCEED_NAME_LENGTH, null)
            }
            if (params.priceString.length > Constants.MAX_PRICE_LENGTH) {
                return Resource.error(ERROR_EXCEED_PRICE_LENGTH, null)
            }
            val amount = params.amountString.toIntOrNull()
                ?: return Resource.error(ERROR_INVALID_AMOUNT, null)
            val shoppingItem = ShoppingItem(
                name = params.name,
                amount = amount as Int,
                price = params.priceString.toFloat(),
                imageUrl = params.imageUrl
            )
            Resource.success(shoppingItem)
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