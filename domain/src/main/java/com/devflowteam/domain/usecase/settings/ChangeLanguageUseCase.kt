package com.devflowteam.domain.usecase.settings

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.DataStoreRepository

class ChangeLanguageUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(language: String) = dataStoreRepository.write(Settings.Language, language)
}