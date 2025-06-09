package com.devflowteam.feature_server

sealed class ServerUIAction {
    data class OnApplyClick(val serverLink: String, ): ServerUIAction()
    data class SeeInstructionsClickAction(val url: String): ServerUIAction()
}