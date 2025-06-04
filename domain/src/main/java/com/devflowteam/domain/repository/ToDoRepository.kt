package com.devflowteam.domain.repository

import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.Result
import com.devflowteam.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    suspend fun upsert(toDo: ToDo): Result<Unit, DataError.Local>

    suspend fun delete(toDo: ToDo): Result<Unit, DataError.Local>

    suspend fun search(toDoId: Int): Result<ToDo, DataError.Local>

    fun getAll(): Result<Flow<List<ToDo>>, DataError.Local>
}