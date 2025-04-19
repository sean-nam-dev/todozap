package com.devflowteam.domain.usecase

import com.devflowteam.domain.repository.ToDoSyncActionRepository

class GetAllToDoSyncActionUseCase(
    private val toDoSyncActionRepository: ToDoSyncActionRepository
) {
    operator fun invoke() = toDoSyncActionRepository.getAll()
}