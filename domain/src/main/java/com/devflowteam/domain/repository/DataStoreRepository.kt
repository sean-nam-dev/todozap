package com.devflowteam.domain.repository

import com.devflowteam.core.utils.Settings
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun <T> read(s: Settings<T>): Flow<T>

    suspend fun <T> write(s: Settings<T>, value: T)
}