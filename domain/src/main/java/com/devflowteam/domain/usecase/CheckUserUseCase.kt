package com.devflowteam.domain.usecase

import com.devflowteam.domain.repository.ApiServiceRepository

class CheckUserUseCase(
    private val apiServiceRepository: ApiServiceRepository
) {
    suspend operator fun invoke(id: String) = apiServiceRepository.checkUser(id)
}