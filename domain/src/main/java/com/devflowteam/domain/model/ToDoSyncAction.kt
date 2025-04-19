package com.devflowteam.domain.model

import com.devflowteam.core.utils.Action

data class ToDoSyncAction(
    val id: Int,
    val toDoId: Int,
    val action: Action
)