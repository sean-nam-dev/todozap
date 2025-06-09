package com.devflowteam.domain.repository

import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.Result
import com.devflowteam.domain.model.ToDoSyncAction
import kotlinx.coroutines.flow.Flow

interface ToDoSyncActionRepository {

    suspend fun upsert(toDoSyncAction: ToDoSyncAction): Result<Unit, DataError.Local>

    suspend fun delete(toDoSyncAction: ToDoSyncAction): Result<Unit, DataError.Local>

    fun getAll(): Result<Flow<List<ToDoSyncAction>>, DataError.Local>

    suspend fun deleteAll(): Result<Unit, DataError.Local>

    suspend fun insert(toDoSyncAction: ToDoSyncAction): Result<Unit, DataError.Local>
}