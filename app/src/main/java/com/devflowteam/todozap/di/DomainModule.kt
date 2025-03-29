package com.devflowteam.todozap.di

import com.devflowteam.domain.usecase.DeleteToDoUseCase
import com.devflowteam.domain.usecase.GetAllToDoUseCase
import com.devflowteam.domain.usecase.InsertToDoUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        InsertToDoUseCase(toDoRepository = get())
    }
    factory {
        DeleteToDoUseCase(toDoRepository = get())
    }
    factory {
        GetAllToDoUseCase(toDoRepository = get())
    }
}