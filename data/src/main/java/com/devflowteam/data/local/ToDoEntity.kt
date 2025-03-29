package com.devflowteam.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devflowteam.domain.model.Status
import java.time.LocalDate

@Entity
data class ToDoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String = "",
    val text: String = "",
    val status: Status = Status.PENDING,
    val date: LocalDate = LocalDate.now().plusDays(1)
)