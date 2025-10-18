package com.devflowteam.feature_start

sealed class StartUIAction {
    data class ChangeServerType(val index: Int): StartUIAction()
    data class DoneServerChangeClickListener(
        val newServer: String,
        val successMessage: String,
        val errorMessage: String
    ): StartUIAction()
    data class SeeInstructions(val url: String): StartUIAction()
    data object RestoreAccountClickListener: StartUIAction()
    data class ApplyClickListener(
        val id: String,
        val successMessage: String,
        val errorMessage: String
    ): StartUIAction()
    data class FinishClickListener(
        val errorMessage: String
    ): StartUIAction()
//    data class ChangeServerTypeAction(val index: Int): StartUIAction()
//    data class SeeInstructionsClickAction(val url: String): StartUIAction()
//    data object FinishClickAction: StartUIAction()
//    data class ApplyIDClickAction(val id: String): StartUIAction()
}