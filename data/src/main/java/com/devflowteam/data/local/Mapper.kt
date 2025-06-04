package com.devflowteam.data.local

import com.devflowteam.core.utils.Action
import com.devflowteam.data.local.todo.ToDoEntity
import com.devflowteam.data.local.todosyncaction.ToDoSyncActionEntity
import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.model.ToDoSyncAction

fun ToDoEntity.toDomain() = ToDo(
    id = id,
    title = title,
    text = text,
    status = status,
    date = date
)

fun ToDo.toEntity() = ToDoEntity(
    id = id,
    title = title,
    text = text,
    status = status,
    date = date
)

fun ToDo.toSyncAction(action: Action) = ToDoSyncAction(
    id = 0,
    toDoId = id,
    action = action
)

fun ToDoSyncActionEntity.toDomain() = ToDoSyncAction(
    id = id,
    toDoId = toDoId,
    action = action
)

fun ToDoSyncAction.toEntity() = ToDoSyncActionEntity(
    id = id,
    toDoId = toDoId,
    action = action
)