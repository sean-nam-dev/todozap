package com.devflowteam.data.repository

import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.Result
import com.devflowteam.data.local.todosyncaction.ToDoSyncActionDao
import com.devflowteam.data.local.toDomain
import com.devflowteam.data.local.toEntity
import com.devflowteam.domain.model.ToDoSyncAction
import com.devflowteam.domain.repository.ToDoSyncActionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ToDoSyncActionRepositoryImpl(
    private val dao: ToDoSyncActionDao
): ToDoSyncActionRepository {

    override suspend fun upsert(toDoSyncAction: ToDoSyncAction): Result<Unit, DataError.Local> {
        return withContext(Dispatchers.IO) {
            try {
                dao.upsert(toDoSyncAction.toEntity())
                Result.Success(Unit)
            } catch (e: OutOfMemoryError) {
                Result.Error(DataError.Local.DISK_FULL)
            } catch (e: Exception) {
                Result.Error(DataError.Local.UNKNOWN)
            }
        }
    }

    override suspend fun delete(toDoSyncAction: ToDoSyncAction): Result<Unit, DataError.Local> {
        return withContext(Dispatchers.IO) {
            try {
                dao.delete(toDoSyncAction.toEntity())
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(DataError.Local.UNKNOWN)
            }
        }
    }

    override fun getAll(): Result<Flow<List<ToDoSyncAction>>, DataError.Local> {
        return try {
            Result.Success(dao.getAll().map { list -> list.map { it.toDomain() } })
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun deleteAll(): Result<Unit, DataError.Local> {
        return withContext(Dispatchers.IO) {
            try {
                dao.deleteAll()
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(DataError.Local.UNKNOWN)
            }
        }
    }

    override suspend fun insert(toDoSyncAction: ToDoSyncAction): Result<Unit, DataError.Local> {
        return withContext(Dispatchers.IO) {
            try {
                dao.insert(toDoSyncAction.toEntity())
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(DataError.Local.UNKNOWN)
            }
        }
    }
}