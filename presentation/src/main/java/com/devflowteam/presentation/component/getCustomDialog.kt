package com.devflowteam.presentation.component

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import com.devflowteam.presentation.R
import com.devflowteam.presentation.utils.getThemeColor
import com.devflowteam.presentation.utils.setTextAppearanceCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun getCustomDialog(
    context: Context,
    innerView: View,
    titleText: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveButtonClickListener: () -> Unit
): AlertDialog {
    val titleTextView = TextView(context).apply {
        setTextAppearanceCompat(context, R.style.TextAppearance_ToDoZap_BodyLarge)
        setTextColor(context.getThemeColor(com.google.android.material.R.attr.colorOnPrimary))
        text = titleText
        setPadding(35)
    }

    return MaterialAlertDialogBuilder(context, R.style.LightDialogTheme)
        .setPositiveButton(positiveButtonText) { _, _ -> onPositiveButtonClickListener() }
        .setNegativeButton(negativeButtonText) { dialog, _ -> dialog.dismiss() }
        .setView(innerView)
        .setCustomTitle(titleTextView)
        .create()
}