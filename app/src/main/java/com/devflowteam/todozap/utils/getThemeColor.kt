package com.devflowteam.todozap.utils

import android.content.Context
import android.util.TypedValue

fun Context.getThemeColor(attr: Int): Int {
    val typedValue = TypedValue()
    val theme = theme
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}