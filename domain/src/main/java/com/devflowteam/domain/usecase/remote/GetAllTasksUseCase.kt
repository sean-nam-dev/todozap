package com.devflowteam.domain.usecase.remote

import com.devflowteam.domain.repository.ApiServiceRepository
import com.devflowteam.domain.usecase.settings.GetIDUseCase
import kotlinx.coroutines.flow.first

class GetAllTasksUseCase(
    private val getIDUseCase: GetIDUseCase,
    private val apiServiceRepository: ApiServiceRepository
) {
    suspend operator fun invoke() = apiServiceRepository.getAllTasks(getIDUseCase().first())
}