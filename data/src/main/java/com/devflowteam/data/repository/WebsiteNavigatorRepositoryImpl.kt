package com.devflowteam.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.devflowteam.domain.repository.WebsiteNavigatorRepository

class WebsiteNavigatorRepositoryImpl(
    private val context: Context
): WebsiteNavigatorRepository {

    override fun openWebsite(url: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        return if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
            true
        } else {
            false
        }
    }
}