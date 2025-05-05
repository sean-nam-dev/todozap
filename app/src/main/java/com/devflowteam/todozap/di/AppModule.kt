package com.devflowteam.todozap.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        com.devflowteam.feature_start.StartViewModel(
            openWebsiteUseCase = get(),
            checkUserUseCase = get(),
            changeServerUseCase = get(),
            changeIDUseCase = get(),
            isFirstLaunchUseCase = get()
        )
    }



//    factory { MyViewModel(get()) }

//    viewModel<SomeViewModel> {
//        SomeViewModel(
//            userCase1 = get(),
//            useCase2 = get()
//        )
//    }
}

// private val vm by viewModel<SomeViewModel>()