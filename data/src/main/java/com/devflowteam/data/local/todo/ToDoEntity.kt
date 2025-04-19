package com.devflowteam.data.local.todo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devflowteam.domain.model.Status

@Entity
data class ToDoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String = "",
    val text: String = "",
    val status: Status = Status.PENDING,
    val date: String = ""
)