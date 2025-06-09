package com.devflowteam.domain.usecase.settings

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.DataStoreRepository
import com.devflowteam.domain.repository.SharedPrefsRepository

class GetFirstLaunchUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository
) {
    operator fun invoke() = sharedPrefsRepository.read(Settings.FirstLaunch)
}