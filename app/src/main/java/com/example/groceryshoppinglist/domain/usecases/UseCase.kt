package com.example.groceryshoppinglist.domain.usecases

import com.example.groceryshoppinglist.data.local.model.ShoppingItem
import com.example.groceryshoppinglist.shared.Resource

interface UseCase<Params, out T> {
    suspend fun execute(params: Params? = null): T
}