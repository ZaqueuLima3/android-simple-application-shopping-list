package com.example.groceryshoppinglist.domain.usecases

import com.example.groceryshoppinglist.data.local.model.ShoppingItem
import com.example.groceryshoppinglist.data.local.model.toDomain
import com.example.groceryshoppinglist.data.repository.ShoppingItemRepository
import com.example.groceryshoppinglist.domain.model.GroceryItem
import com.example.groceryshoppinglist.shared.Constants
import com.example.groceryshoppinglist.shared.Resource

class InsertShoppingItemUseCase(
    private val shoppingItemRepository: ShoppingItemRepository
) : UseCase<InsertShoppingItemUseCase.Params, Resource<GroceryItem>> {
    override suspend fun execute(params: Params?): Resource<GroceryItem> {
        return try {
            requireNotNull(params) { "Params must not be null." }
            if (params.name.isBlank() || params.amountString.isBlank() || params.priceString.isBlank()) {
                return  Resource.error(ERROR_EMPTY_FIELD)
            }
            if (params.name.length > Constants.MAX_NAME_LENGTH) {
                return Resource.error(ERROR_EXCEED_NAME_LENGTH)
            }
            if (params.priceString.length > Constants.MAX_PRICE_LENGTH) {
                return Resource.error(ERROR_EXCEED_PRICE_LENGTH)
            }
            val amount = params.amountString.toIntOrNull()
                ?: return Resource.error(ERROR_INVALID_AMOUNT)
            val shoppingItem = ShoppingItem(
                name = params.name,
                amount = amount,
                price = params.priceString.toFloat(),
                imageUrl = params.imageUrl
            )
            shoppingItemRepository.insertShoppingItem(shoppingItem)
            Resource.success(shoppingItem.toDomain())
        } catch (e: Exception) {
            Resource.error(e.message.toString())
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
