package com.devflowteam.domain.usecase.network

import com.devflowteam.domain.repository.NetworkMonitorRepository

class NetworkMonitorReadUseCase(
    private val networkMonitorRepository: NetworkMonitorRepository
) {
    operator fun invoke() = networkMonitorRepository.read()
}