package com.devflowteam.data.local

import com.devflowteam.domain.model.ToDo

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