package com.devflowteam.domain.usecase

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.SettingsRepository

class ChangeLanguageUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(language: String) = settingsRepository.write(Settings.Language, language)
}