package com.example.groceryshoppinglist.domain.usecases

interface UseCase<Params, out T> {
    suspend fun execute(params: Params? = null): T
}