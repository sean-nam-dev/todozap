package com.devflowteam.domain.usecase.local.todosync

import com.devflowteam.domain.model.ToDoSyncAction
import com.devflowteam.domain.repository.ToDoSyncActionRepository

class InsertToDoSyncActionUseCase(
    private val repository: ToDoSyncActionRepository
) {
    suspend operator fun invoke(toDoSyncAction: ToDoSyncAction) = repository.insert(toDoSyncAction)
}