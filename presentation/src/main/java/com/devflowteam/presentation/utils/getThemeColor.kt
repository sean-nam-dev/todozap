package com.devflowteam.presentation.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

fun Context.getThemeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    val theme = theme
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}