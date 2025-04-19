package com.devflowteam.data.remote.request

import com.devflowteam.domain.model.ToDo
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class TaskRequest(

    @SerializedName("user_id")
    val userId: String,

    @SerializedName("task_data")
    val taskData: ToDo
) {
    fun toJson(): TaskRequestSerialized {
        return TaskRequestSerialized(
            userId = userId,
            taskData = Gson().toJson(taskData)
        )
    }
}

data class TaskRequestSerialized(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("task_data")
    val taskData: String
)