package com.devflowteam.domain.usecase.local.todosync

import com.devflowteam.domain.model.ToDoSyncAction
import com.devflowteam.domain.repository.ToDoSyncActionRepository

class UpsertToDoSyncActionUseCase(
    private val toDoSyncActionRepository: ToDoSyncActionRepository
) {
    suspend operator fun invoke(toDoSyncAction: ToDoSyncAction) = toDoSyncActionRepository.upsert(toDoSyncAction)
}