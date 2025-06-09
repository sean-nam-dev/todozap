package com.devflowteam.presentation.utils

import android.content.Context
import android.os.Build
import android.widget.TextView

fun TextView.setTextAppearanceCompat(
    context: Context,
    styleRes: Int
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(styleRes)
    } else {
        @Suppress("DEPRECATION")
        setTextAppearance(context, styleRes)
    }
}