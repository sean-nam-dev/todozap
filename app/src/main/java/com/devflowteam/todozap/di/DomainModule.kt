package com.devflowteam.todozap.di

import com.devflowteam.domain.usecase.ChangeDarkModeUseCase
import com.devflowteam.domain.usecase.ChangeFirstLaunchUseCase
import com.devflowteam.domain.usecase.ChangeIDUseCase
import com.devflowteam.domain.usecase.ChangeServerUseCase
import com.devflowteam.domain.usecase.DeleteToDoUseCase
import com.devflowteam.domain.usecase.GetAllToDoUseCase
import com.devflowteam.domain.usecase.GetDarkModeUseCase
import com.devflowteam.domain.usecase.GetFirstLaunchUseCase
import com.devflowteam.domain.usecase.GetIDUseCase
import com.devflowteam.domain.usecase.GetServerUseCase
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
}