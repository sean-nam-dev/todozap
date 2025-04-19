package com.devflowteam.todozap.di

import com.devflowteam.domain.usecase.AddToDoUseCase
import com.devflowteam.domain.usecase.AddUserUseCase
import com.devflowteam.domain.usecase.ChangeDarkModeUseCase
import com.devflowteam.domain.usecase.ChangeFirstLaunchUseCase
import com.devflowteam.domain.usecase.ChangeIDUseCase
import com.devflowteam.domain.usecase.ChangeServerUseCase
import com.devflowteam.domain.usecase.DeleteTaskUseCase
import com.devflowteam.domain.usecase.DeleteToDoSyncActionUseCase
import com.devflowteam.domain.usecase.DeleteToDoUseCase
import com.devflowteam.domain.usecase.GetAllTasksUseCase
import com.devflowteam.domain.usecase.GetAllToDoSyncActionUseCase
import com.devflowteam.domain.usecase.GetAllToDoUseCase
import com.devflowteam.domain.usecase.GetDarkModeUseCase
import com.devflowteam.domain.usecase.GetFirstLaunchUseCase
import com.devflowteam.domain.usecase.GetIDUseCase
import com.devflowteam.domain.usecase.GetServerUseCase
import com.devflowteam.domain.usecase.UpsertToDoUseCase
import com.devflowteam.domain.usecase.UpdateTaskUseCase
import com.devflowteam.domain.usecase.UpsertToDoSyncActionUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        UpsertToDoUseCase(toDoRepository = get())
    }
    factory {
        DeleteToDoUseCase(toDoRepository = get())
    }
    factory {
        GetAllToDoUseCase(toDoRepository = get())
    }
    factory {
        GetServerUseCase(settingsRepository = get())
    }
    factory {
        ChangeServerUseCase(settingsRepository = get())
    }
    factory {
        GetIDUseCase(settingsRepository = get())
    }
    factory {
        ChangeIDUseCase(settingsRepository = get())
    }
    factory {
        GetDarkModeUseCase(settingsRepository = get())
    }
    factory {
        ChangeDarkModeUseCase(settingsRepository = get())
    }
    factory {
        GetFirstLaunchUseCase(settingsRepository = get())
    }
    factory {
        ChangeFirstLaunchUseCase(settingsRepository = get())
    }
    factory {
        GetAllTasksUseCase(
            getIDUseCase = get(),
            apiServiceRepository = get()
        )
    }
    factory {
        AddUserUseCase(
            getIDUseCase = get(),
            apiServiceRepository = get()
        )
    }
    factory {
        AddToDoUseCase(
            getIDUseCase = get(),
            apiServiceRepository = get()
        )
    }
    factory {
        UpdateTaskUseCase(
            getIDUseCase = get(),
            apiServiceRepository = get()
        )
    }
    factory {
        DeleteTaskUseCase(
            getIDUseCase = get(),
            apiServiceRepository = get()
        )
    }
    factory {
        UpsertToDoSyncActionUseCase(
            toDoSyncActionRepository = get()
        )
    }
    factory {
        DeleteToDoSyncActionUseCase(
            toDoSyncActionRepository = get()
        )
    }
    factory {
        GetAllToDoSyncActionUseCase(
            toDoSyncActionRepository = get()
        )
    }
    factory {
        UpsertToDoUseCase(
            toDoRepository = get()
        )
    }
}