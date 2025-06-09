package com.devflowteam.domain.usecase.settings

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.DataStoreRepository

class GetServerUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.read(Settings.Server)
}