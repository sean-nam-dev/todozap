package com.devflowteam.todozap.di

import androidx.room.Room
import com.devflowteam.data.local.ToDoDao
import com.devflowteam.data.local.ToDoDatabase
import com.devflowteam.data.repository.SettingsRepositoryImpl
import com.devflowteam.data.repository.ToDoRepositoryImpl
import com.devflowteam.domain.repository.SettingsRepository
import com.devflowteam.domain.repository.ToDoRepository
import org.koin.dsl.module

val dataModule = module {

    single<ToDoDao> {
        Room.databaseBuilder(
            context = get(),
            klass = ToDoDatabase::class.java,
            "todo.db"
        ).build().dao
    }

    single<ToDoRepository> {
        ToDoRepositoryImpl(dao = get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(context = get())
    }
}