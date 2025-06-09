package com.devflowteam.domain.usecase.remote

import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.repository.ApiServiceRepository
import com.devflowteam.domain.usecase.settings.GetIDUseCase
import kotlinx.coroutines.flow.first

class AddTaskUseCase(
    private val getIDUseCase: GetIDUseCase,
    private val apiServiceRepository: ApiServiceRepository
) {
    suspend operator fun invoke(taskData: ToDo) =
        apiServiceRepository.addTask(getIDUseCase.invoke().first(), taskData)
}