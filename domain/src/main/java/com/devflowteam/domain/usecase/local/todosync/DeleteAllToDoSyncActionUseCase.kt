package com.devflowteam.domain.usecase.local.todosync

import com.devflowteam.domain.repository.ToDoSyncActionRepository

class DeleteAllToDoSyncActionUseCase(
    private val repository: ToDoSyncActionRepository
) {
    suspend operator fun invoke() = repository.deleteAll()
}