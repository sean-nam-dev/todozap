package com.devflowteam.data.local.todosyncaction

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoSyncActionDao {

    @Upsert
    suspend fun upsert(toDoSyncActionEntity: ToDoSyncActionEntity)

    @Delete
    suspend fun delete(toDoSyncActionEntity: ToDoSyncActionEntity)

    @Query("SELECT * FROM todosyncactionentity ORDER BY id ASC")
    fun getAll(): Flow<List<ToDoSyncActionEntity>>
}