package com.devflowteam.domain.usecase.local.todo

import com.devflowteam.domain.repository.ToDoRepository

class SearchToDoUseCase(
    private val toDoRepository: ToDoRepository
) {
    suspend operator fun invoke(toDoId: Int) = toDoRepository.search(toDoId)
}