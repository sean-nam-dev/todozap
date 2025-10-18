package com.devflowteam.data.repository

import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.DataSuccess
import com.devflowteam.core.common.Result
import com.devflowteam.data.remote.ApiService
import com.devflowteam.data.remote.request.TaskRequest
import com.devflowteam.data.remote.request.UserRequest
import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.repository.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class ApiServiceRepositoryImpl(
    private val apiService: ApiService
): ApiServiceRepository {

    override suspend fun addUser(id: String): Result<DataSuccess.Network, DataError.Network> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.addUser(UserRequest(id))

                if (response.isSuccessful) {
                    val codeResponse = response.body()

                    when (codeResponse?.code) {
                        0 -> Result.Success(DataSuccess.Network.INSERTED)
                        8 -> Result.Success(DataSuccess.Network.EXISTS)
                        else -> Result.Error(DataError.Network.EXTERNAL)
                    }

                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                Result.Error(DataError.Network.NO_SERVER_AVAILABLE)
            }
        }
    }

    override suspend fun checkUser(id: String): Result<Unit, DataError.Network> {
        if (!isValidUUID(id)) {
            return Result.Error(DataError.Network.UNKNOWN)
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.checkUser(id)

                if (response.isSuccessful) {
                    val codeResponse = response.body()

                    when (codeResponse?.code) {
                        0 -> Result.Success(Unit)
                        else -> Result.Error(DataError.Network.EXTERNAL)
                    }
                } else {
                    throw Exception()
                }

            } catch (e: Exception) {
                Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }

    override suspend fun getAllTasks(id: String): Result<List<ToDo>, DataError.Network> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAllTasks(id)

                if (response.isSuccessful) {
                    val getTaskResponse = response.body()

                    when (getTaskResponse?.code) {
                        0 -> Result.Success(getTaskResponse.tasks ?: emptyList())
                        else -> Result.Error(DataError.Network.EXTERNAL)
                    }
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }

    override suspend fun addTask(
        userId: String,
        taskData: ToDo
    ): Result<Unit, DataError.Network> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.addTask(taskData.toRequest(userId))

                if (response.isSuccessful) {
                    val codeResponse = response.body()

                    when (codeResponse?.code) {
                        0 -> Result.Success(Unit)
                        else -> Result.Error(DataError.Network.EXTERNAL)
                    }
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }

    override suspend fun updateTask(
        userId: String,
        taskData: ToDo
    ): Result<Unit, DataError.Network> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.updateTask(taskData.toRequest(userId))

                if (response.isSuccessful) {
                    val codeResponse = response.body()

                    when (codeResponse?.code) {
                        0 -> Result.Success(Unit)
                        else -> Result.Error(DataError.Network.EXTERNAL)
                    }
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }

    override suspend fun deleteTask(
        userId: String,
        taskData: ToDo
    ): Result<Unit, DataError.Network> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.deleteTask(taskData.toRequest(userId))

                if (response.isSuccessful) {
                    val codeResponse = response.body()

                    when (codeResponse?.code) {
                        0 -> Result.Success(Unit)
                        else -> Result.Error(DataError.Network.EXTERNAL)
                    }
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }

    override suspend fun upsertTask(
        userId: String,
        taskData: ToDo
    ): Result<Unit, DataError.Network> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.upsertTask(taskData.toRequest(userId))

                if (response.isSuccessful) {
                    val codeResponse = response.body()

                    when (codeResponse?.code) {
                        0 -> Result.Success(Unit)
                        else -> Result.Error(DataError.Network.EXTERNAL)
                    }
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }

    private fun isValidUUID(id: String): Boolean {
        return try {
            UUID.fromString(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}

fun ToDo.toRequest(userId: String) = TaskRequest(userId, this).toJson()