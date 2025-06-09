package com.devflowteam.domain.usecase.local.todo

import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.repository.ToDoRepository

class DeleteToDoUseCase(
    private val toDoRepository: ToDoRepository
) {
    suspend operator fun invoke(toDo: ToDo) = toDoRepository.delete(toDo)
}