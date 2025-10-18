package com.devflowteam.feature_server

sealed class ServerUIAction {
    data class DoneServerChangeClickListener(
        val newServer: String,
        val successMessage: String,
        val errorMessage: String
    ): ServerUIAction()
    data class SeeInstructionsClickAction(val url: String): ServerUIAction()
}