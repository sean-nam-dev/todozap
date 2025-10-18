package com.devflowteam.data.repository

import com.devflowteam.data.remote.NetworkMonitor
import com.devflowteam.domain.repository.NetworkMonitorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class NetworkMonitorRepositoryImpl(
    private val networkMonitor: NetworkMonitor
): NetworkMonitorRepository {

    override fun read(): StateFlow<Boolean> = networkMonitor.isConnected

    override fun unregister() = networkMonitor.unregister()
}