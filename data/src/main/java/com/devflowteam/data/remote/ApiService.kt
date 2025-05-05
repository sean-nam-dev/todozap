package com.devflowteam.data.remote

import com.devflowteam.data.remote.request.TaskRequestSerialized
import com.devflowteam.data.remote.request.UserRequest
import com.devflowteam.data.remote.response.CodeResponse
import com.devflowteam.data.remote.response.GetTasksResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("add_user.php")
    suspend fun addUser(@Body request: UserRequest) : Response<CodeResponse>

    @GET("check_user.php")
    suspend fun checkUser(@Query("user_id") userId: String) : Response<CodeResponse>

    @GET("get_all_tasks.php")
    suspend fun getAllTasks(@Query("user_id") userId: String) : Response<GetTasksResponse>

    @POST("add_task.php")
    suspend fun addTask(@Body request: TaskRequestSerialized) : Response<CodeResponse>

    @POST("update_task")
    suspend fun updateTask(@Body request: TaskRequestSerialized) : Response<CodeResponse>

    @POST("delete_task")
    suspend fun deleteTask(@Body request: TaskRequestSerialized) : Response<CodeResponse>

    @POST("upsert_task")
    suspend fun upsertTask(@Body request: TaskRequestSerialized) : Response<CodeResponse>
}