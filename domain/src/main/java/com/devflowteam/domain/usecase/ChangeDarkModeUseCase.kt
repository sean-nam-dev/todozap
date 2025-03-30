package com.devflowteam.domain.usecase

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.SettingsRepository

class ChangeDarkModeUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(isDarkMode: Boolean) =
        settingsRepository.write(Settings.DarkMode, isDarkMode)
}