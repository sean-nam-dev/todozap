package com.devflowteam.domain.repository

import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.DataSuccess
import com.devflowteam.core.common.Result
import com.devflowteam.domain.model.ToDo

interface ApiServiceRepository {

    suspend fun addUser(id: String): Result<DataSuccess.Network, DataError.Network>

    suspend fun checkUser(id: String): Result<Unit, DataError.Network>

    suspend fun getAllTasks(id: String): Result<List<ToDo>, DataError.Network>

    suspend fun addTask(
        userId: String,
        taskData: ToDo
    ): Result<Unit, DataError.Network>

    suspend fun updateTask(
        userId: String,
        taskData: ToDo
    ): Result<Unit, DataError.Network>

    suspend fun deleteTask(
        userId: String,
        taskData: ToDo
    ): Result<Unit, DataError.Network>

    suspend fun upsertTask(
        userId: String,
        taskData: ToDo
    ): Result<Unit, DataError.Network>
}