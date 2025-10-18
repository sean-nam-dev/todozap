package com.devflowteam.todozap.di

import com.devflowteam.feature_creation.CreationViewModel
import com.devflowteam.feature_home.ui.home.HomeViewModel
import com.devflowteam.feature_language.LanguageViewModel
import com.devflowteam.feature_server.ServerViewModel
import com.devflowteam.feature_start.StartViewModel
import com.devflowteam.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        StartViewModel(
            migrateServerUseCase = get(),
            checkUserUseCase = get(),
            changeIDUseCase = get(),
            addUserUseCase = get(),
            changeFirstLaunchUseCase = get()
        )
    }

    viewModel {
        HomeViewModel(
            getAllToDoUseCase = get(),
            upsertToDoUseCase = get(),
            deleteToDoUseCase = get(),
            upsertToDoSyncActionUseCase = get(),
        )
    }
    
    viewModel { 
        LanguageViewModel(
            getLanguageUseCase = get(),
            changeLanguageUseCase = get(),
        )
    }

    viewModel {
        ServerViewModel(
            migrateServerUseCase = get()
        )
    }

    viewModel {
        MainViewModel(
            changeLanguageUseCase = get(),
            getFirstLaunchUseCase = get(),
            getIDUseCase = get(),
        )
    }

    viewModel {
        CreationViewModel(
            upsertToDoUseCase = get()
        )
    }
}
