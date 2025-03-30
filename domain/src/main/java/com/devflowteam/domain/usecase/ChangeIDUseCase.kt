package com.devflowteam.domain.usecase

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.SettingsRepository

class ChangeIDUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(new: String) =
        settingsRepository.write(Settings.ID, new)
}