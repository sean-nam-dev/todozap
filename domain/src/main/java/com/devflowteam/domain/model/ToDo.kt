package com.devflowteam.domain.model

import java.time.LocalDate

data class ToDo(
    val id: Int,
    val title: String,
    val text: String,
    val status: Status,
    val date: LocalDate
)