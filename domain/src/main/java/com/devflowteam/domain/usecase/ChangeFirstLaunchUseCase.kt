package com.devflowteam.domain.usecase

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.SettingsRepository

class ChangeFirstLaunchUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(isFirstLaunch: Boolean) =
        settingsRepository.write(Settings.FirstLaunch, isFirstLaunch)
}