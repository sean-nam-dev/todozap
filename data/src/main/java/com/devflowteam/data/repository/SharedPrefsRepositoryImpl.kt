package com.devflowteam.data.repository

import android.content.SharedPreferences
import com.devflowteam.core.utils.Settings
import com.devflowteam.domain.repository.SharedPrefsRepository

@Suppress("UNCHECKED_CAST")
class SharedPrefsRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SharedPrefsRepository {

    override fun <T> read(s: Settings<T>): T {
        return when (s.defaultValue) {
            is Boolean -> sharedPreferences.getBoolean(s.key, s.defaultValue as Boolean) as T
            is Int -> sharedPreferences.getInt(s.key, s.defaultValue as Int) as T
            is Long -> sharedPreferences.getLong(s.key, s.defaultValue as Long) as T
            is Float -> sharedPreferences.getFloat(s.key, s.defaultValue as Float) as T
            is String -> sharedPreferences.getString(s.key, s.defaultValue as String) as T
            is Set<*> -> sharedPreferences.getStringSet(s.key, s.defaultValue as Set<String>) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    override fun <T> write(s: Settings<T>, value: T) {
        return when (s.defaultValue) {
            is Boolean -> sharedPreferences.edit().putBoolean(s.key, value as Boolean)
            is Int -> sharedPreferences.edit().putInt(s.key, value as Int)
            is Long -> sharedPreferences.edit().putLong(s.key, value as Long)
            is Float -> sharedPreferences.edit().putFloat(s.key, value as Float)
            is String -> sharedPreferences.edit().putString(s.key, value as String)
            is Set<*> -> sharedPreferences.edit().putStringSet(s.key, value as Set<String>)
            else -> throw IllegalArgumentException("Unsupported type")
        }.apply()
    }
}