package com.devflowteam.domain.repository

import com.devflowteam.core.utils.Settings

interface SharedPrefsRepository {

    fun <T> read(s: Settings<T>): T

    fun <T> write(s: Settings<T>, value: T)
}