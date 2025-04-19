package com.devflowteam.domain.usecase

import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.repository.ApiServiceRepository
import kotlinx.coroutines.flow.first

class AddToDoUseCase(
    private val getIDUseCase: GetIDUseCase,
    private val apiServiceRepository: ApiServiceRepository
) {
    suspend operator fun invoke(taskData: ToDo) =
        apiServiceRepository.addTask(getIDUseCase.invoke().first(), taskData)
}