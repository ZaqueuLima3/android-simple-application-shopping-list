package com.example.groceryshoppinglist.domain.usecases

import com.example.groceryshoppinglist.data.repository.ImageRepository
import com.example.groceryshoppinglist.domain.model.Image
import com.example.groceryshoppinglist.shared.Resource
import javax.inject.Inject

class SearchImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) : UseCase<SearchImageUseCase.Params, Resource<List<Image>>> {
    override suspend fun execute(params: Params?): Resource<List<Image>> {
        requireNotNull(params) { "Params must not be null." }
        return imageRepository.searchForImage(params.query)
    }

    data class Params(
        val query: String
    )
}
