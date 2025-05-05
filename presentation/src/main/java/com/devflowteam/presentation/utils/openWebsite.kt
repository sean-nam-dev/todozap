package com.devflowteam.presentation.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openWebsite(url: String): Boolean {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    return try {
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}