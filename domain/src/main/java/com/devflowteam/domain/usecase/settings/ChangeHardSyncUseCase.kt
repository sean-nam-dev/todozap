package com.devflowteam.domain.usecase.settings

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.DataStoreRepository

class ChangeHardSyncUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(value: Boolean) = dataStoreRepository.write(Settings.HardSync, value)
}