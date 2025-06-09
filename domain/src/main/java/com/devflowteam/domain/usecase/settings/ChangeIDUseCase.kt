package com.devflowteam.domain.usecase.settings

import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.DataStoreRepository

class ChangeIDUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(new: String) =
        dataStoreRepository.write(Settings.ID, new)
}