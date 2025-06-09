package com.devflowteam.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass


@Suppress("UNCHECKED_CAST")
class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    override fun <T> read(s: Settings<T>): Flow<T> {
        val preferencesKey = getPreferencesKey(s.key, s.defaultValue!!::class)

        return dataStore.data
            .map { preferences ->
                if (preferences[preferencesKey] == null) {
                    write(s, s.defaultValue)
                }
                preferences[preferencesKey] ?: s.defaultValue
            }
    }

    override suspend fun <T> write(
        s: Settings<T>,
        value: T
    ) {
        val preferencesKey = getPreferencesKey(s.key, value!!::class)

        dataStore.edit { preferences ->
            when (value) {
                is Boolean -> preferences[preferencesKey] = value
                is Int -> preferences[preferencesKey] = value
                is Long -> preferences[preferencesKey] = value
                is Float -> preferences[preferencesKey] = value
                is String -> preferences[preferencesKey] = value
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }

    private fun <T : Any> getPreferencesKey(
        key: String,
        type: KClass<out T>
    ): Preferences.Key<T> {
        return when (type) {
            Boolean::class -> booleanPreferencesKey(key)
            Int::class -> intPreferencesKey(key)
            Long::class -> longPreferencesKey(key)
            Float::class -> floatPreferencesKey(key)
            String::class -> stringPreferencesKey(key)
            else -> throw IllegalArgumentException("Unsupported type")
        } as Preferences.Key<T>
    }
}