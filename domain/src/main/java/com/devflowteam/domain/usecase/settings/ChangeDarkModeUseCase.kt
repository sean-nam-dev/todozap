package com.devflowteam.domain.usecase.settings

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.DataStoreRepository

class ChangeDarkModeUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(isDarkMode: Boolean) =
        dataStoreRepository.write(Settings.DarkMode, isDarkMode)
}