package com.devflowteam.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitorRepository {

    fun read(): StateFlow<Boolean>

    fun unregister()
}