package com.devflowteam.domain.usecase.local.todo

import com.devflowteam.domain.repository.ToDoRepository

class GetAllToDoUseCase(
    private val toDoRepository: ToDoRepository
) {
    operator fun invoke() = toDoRepository.getAll()
}