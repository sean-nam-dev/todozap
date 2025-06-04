package com.devflowteam.domain.usecase

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.SettingsRepository

class ChangeHardSyncUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: Boolean) = settingsRepository.write(Settings.HardSync, value)
}