package com.devflowteam.data.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.devflowteam.core.utils.Settings
import com.devflowteam.data.local.dataStore
import com.devflowteam.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlin.reflect.KClass

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    override fun <T> read(s: Settings<T>): Flow<T> {
        val preferencesKey = getPreferencesKey(s.key, s.defaultValue!!::class)

        return context.dataStore.data
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

        context.dataStore.edit { preferences ->
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