package com.devflowteam.data.remote.response

import com.devflowteam.domain.model.ToDo
import com.google.gson.annotations.SerializedName

data class GetTasksResponse(

    @SerializedName("code")
    val code: Int,

    @SerializedName("tasks")
    val tasks: List<ToDo>? = null
)
