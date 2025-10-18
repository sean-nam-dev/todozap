package com.devflowteam.domain.usecase.local.todo

import com.devflowteam.core.common.Result
import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.repository.ToDoRepository

class UpsertToDoUseCase(
    private val toDoRepository: ToDoRepository
) {
    suspend operator fun invoke(toDo: ToDo): Boolean {
        return toDo.title.isNotBlank() &&
            toDo.text.isNotBlank() &&
            toDo.date.isNotBlank() &&
            toDoRepository.upsert(toDo) is Result.Success
    }
}