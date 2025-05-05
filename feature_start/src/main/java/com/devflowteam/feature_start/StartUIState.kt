package com.devflowteam.feature_start

import com.devflowteam.core.utils.Settings
import java.util.UUID

data class StartUIState(
    val serverLink: String = Settings.Server.defaultValue,
    val id: String = UUID.randomUUID().toString(),
    val isLinkInputVisible: Boolean = false
)