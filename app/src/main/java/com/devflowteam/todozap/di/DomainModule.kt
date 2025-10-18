package com.devflowteam.todozap.di

import com.devflowteam.domain.usecase.MigrateServerUseCase
import com.devflowteam.domain.usecase.remote.AddTaskUseCase
import com.devflowteam.domain.usecase.remote.AddUserUseCase
import com.devflowteam.domain.usecase.settings.ChangeDarkModeUseCase
import com.devflowteam.domain.usecase.settings.ChangeFirstLaunchUseCase
import com.devflowteam.domain.usecase.settings.ChangeHardSyncUseCase
import com.devflowteam.domain.usecase.settings.ChangeIDUseCase
import com.devflowteam.domain.usecase.settings.ChangeLanguageUseCase
import com.devflowteam.domain.usecase.settings.ChangeServerUseCase
import com.devflowteam.domain.usecase.remote.CheckUserUseCase
import com.devflowteam.domain.usecase.remote.DeleteTaskUseCase
import com.devflowteam.domain.usecase.local.todosync.DeleteToDoSyncActionUseCase
import com.devflowteam.domain.usecase.local.todo.DeleteToDoUseCase
import com.devflowteam.domain.usecase.remote.GetAllTasksUseCase
import com.devflowteam.domain.usecase.local.todosync.GetAllToDoSyncActionUseCase
import com.devflowteam.domain.usecase.local.todo.GetAllToDoUseCase
import com.devflowteam.domain.usecase.settings.GetDarkModeUseCase
import com.devflowteam.domain.usecase.settings.GetIDUseCase
import com.devflowteam.domain.usecase.settings.GetLanguageUseCase
import com.devflowteam.domain.usecase.settings.GetServerUseCase
import com.devflowteam.domain.usecase.local.todo.SearchToDoUseCase
import com.devflowteam.domain.usecase.remote.UpdateTaskUseCase
import com.devflowteam.domain.usecase.remote.UpsertTaskUseCase
import com.devflowteam.domain.usecase.local.todosync.UpsertToDoSyncActionUseCase
import com.devflowteam.domain.usecase.local.todo.UpsertToDoUseCase
import com.devflowteam.domain.usecase.local.todosync.DeleteAllToDoSyncActionUseCase
import com.devflowteam.domain.usecase.local.todosync.InsertToDoSyncActionUseCase
import com.devflowteam.domain.usecase.settings.GetFirstLaunchUseCase
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
        GetServerUseCase(dataStoreRepository = get())
    }
    factory {
        ChangeServerUseCase(dataStoreRepository = get())
    }
    factory {
        GetIDUseCase(dataStoreRepository = get())
    }
    factory {
        ChangeIDUseCase(dataStoreRepository = get())
    }
    factory {
        GetDarkModeUseCase(dataStoreRepository = get())
    }
    factory {
        ChangeDarkModeUseCase(dataStoreRepository = get())
    }
    factory {
        GetFirstLaunchUseCase(sharedPrefsRepository = get())
    }
    factory {
        ChangeFirstLaunchUseCase(sharedPrefsRepository = get())
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
        AddTaskUseCase(
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
        InsertToDoSyncActionUseCase(
            repository = get()
        )
    }
    factory {
        DeleteAllToDoSyncActionUseCase(
            repository = get()
        )
    }
    factory {
        DeleteToDoUseCase(
            toDoRepository = get()
        )
    }
    factory {
        UpsertToDoUseCase(
            toDoRepository = get()
        )
    }
    factory {
        CheckUserUseCase(
            apiServiceRepository = get()
        )
    }
    factory {
        SearchToDoUseCase(
            toDoRepository = get()
        )
    }
    factory {
        UpsertTaskUseCase(
            getIDUseCase = get(),
            apiServiceRepository = get()
        )
    }
    factory {
        ChangeHardSyncUseCase(
            dataStoreRepository = get()
        )
    }
    factory {
        GetLanguageUseCase(
            dataStoreRepository = get()
        )
    }
    factory {
        ChangeLanguageUseCase(
            dataStoreRepository = get()
        )
    }
    factory {
        MigrateServerUseCase(
            changeServerUseCase = get(),
            addUserUseCase = get(),
            getAllToDoUseCase = get(),
            upsertTaskUseCase = get(),
            deleteAllToDoSyncActionUseCase = get(),
            insertToDoSyncActionUseCase = get()
        )
    }
}