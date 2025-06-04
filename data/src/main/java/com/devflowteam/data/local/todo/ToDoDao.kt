package com.devflowteam.data.local.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Upsert
    suspend fun insert(toDoEntity: ToDoEntity)

    @Delete
    suspend fun delete(toDoEntity: ToDoEntity)

    @Query("SELECT * FROM ToDoEntity WHERE id = :todoId LIMIT 1")
    fun search(todoId: Int): ToDoEntity?

    @Query("SELECT * FROM ToDoEntity ORDER BY id ASC")
    fun getAll(): Flow<List<ToDoEntity>>
}