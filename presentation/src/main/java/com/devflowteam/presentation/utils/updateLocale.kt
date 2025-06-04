package com.devflowteam.presentation.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

fun Context.updateLocale(code: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSystemService(LocaleManager::class.java)
            .applicationLocales = LocaleList.forLanguageTags(code)
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
    }
}