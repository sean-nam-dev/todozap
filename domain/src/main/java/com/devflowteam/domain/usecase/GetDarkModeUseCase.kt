package com.devflowteam.domain.usecase

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.SettingsRepository

class GetDarkModeUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.read(Settings.DarkMode)
}