package com.devflowteam.presentation.utils

import android.content.Context
import androidx.annotation.StringRes

interface ResourceManager {
    fun getString(@StringRes id: Int): String
}

class ContextResourceManager(private val appContext: Context): ResourceManager {
    override fun getString(id: Int): String = appContext.getString(id)
}