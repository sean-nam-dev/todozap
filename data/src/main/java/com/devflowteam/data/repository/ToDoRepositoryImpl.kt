package com.devflowteam.data.repository

import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.Result
import com.devflowteam.data.local.ToDoDao
import com.devflowteam.data.local.toDomain
import com.devflowteam.data.local.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ToDoRepositoryImpl(
    private val dao: ToDoDao
): ToDoRepository {
    override suspend fun insert(toDo: ToDo): Result<Unit, DataError.Local> {
        return withContext(Dispatchers.IO) {
            try {
                dao.insert(toDo.toEntity())
                Result.Success(Unit)
            } catch (e: OutOfMemoryError) {
                Result.Error(DataError.Local.DISK_FULL)
            } catch (e: Exception) {
                Result.Error(DataError.Local.UNKNOWN)
            }
        }
    }

    override suspend fun delete(toDo: ToDo): Result<Unit, DataError.Local> {
        return withContext(Dispatchers.IO) {
            try {
                dao.delete(toDo.toEntity())
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(DataError.Local.UNKNOWN)
            }
        }
    }

    override fun getAll(): Result<Flow<List<ToDo>>, DataError.Local> {
        return try {
            Result.Success(dao.getAll().map { list -> list.map { it.toDomain() } })
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}