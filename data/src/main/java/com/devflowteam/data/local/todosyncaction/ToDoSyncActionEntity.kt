package com.devflowteam.data.local.todosyncaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devflowteam.core.utils.Action

@Entity
class ToDoSyncActionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val toDoId: Int,
    val action: Action
)