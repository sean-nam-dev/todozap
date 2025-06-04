package com.devflowteam.data.repository

import androidx.room.withTransaction
import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.Result
import com.devflowteam.core.utils.Action
import com.devflowteam.data.local.todo.ToDoDao
import com.devflowteam.data.local.todo.ToDoDatabase
import com.devflowteam.data.local.todosyncaction.ToDoSyncActionDao
import com.devflowteam.data.local.todosyncaction.ToDoSyncActionEntity
import com.devflowteam.data.local.toDomain
import com.devflowteam.data.local.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ToDoRepositoryImpl(
    private val toDoDb: ToDoDatabase,
    private val toDoDao: ToDoDao,
    private val toDoSyncActionDao: ToDoSyncActionDao
): ToDoRepository {

    override suspend fun upsert(toDo: ToDo): Result<Unit, DataError.Local> {
        return withContext(Dispatchers.IO) {
            try {
                toDoDb.withTransaction {
                    toDoDao.insert(toDo.toEntity())
                    toDoSyncActionDao.upsert(
                        ToDoSyncActionEntity(
                            toDoId = toDo.id,
                            action = Action.UPSERT
                        )
                    )
                }
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
                toDoDao.delete(toDo.toEntity())
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(DataError.Local.UNKNOWN)
            }
        }
    }

    override suspend fun search(toDoId: Int): Result<ToDo, DataError.Local> {
        return withContext(Dispatchers.IO) {
            try {
                val result = toDoDao.search(toDoId)

                if (result != null) {
                    Result.Success(result.toDomain())
                } else {
                    throw IllegalStateException("ToDoRepositoryImpl - search (result is null)")
                }
            } catch (e: Exception) {
                Result.Error(DataError.Local.UNKNOWN)
            }
        }
    }

    override fun getAll(): Result<Flow<List<ToDo>>, DataError.Local> {
        return try {
            Result.Success(toDoDao.getAll().map { list -> list.map { it.toDomain() } })
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}