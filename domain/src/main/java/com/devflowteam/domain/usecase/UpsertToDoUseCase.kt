package com.devflowteam.domain.usecase

import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.repository.ToDoRepository

class UpsertToDoUseCase(
    private val toDoRepository: ToDoRepository
) {
    suspend operator fun invoke(toDo: ToDo) = toDoRepository.upsert(toDo)
}