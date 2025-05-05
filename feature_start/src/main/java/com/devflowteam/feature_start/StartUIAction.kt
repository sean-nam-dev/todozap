package com.devflowteam.feature_start

sealed class StartUIAction {
    data class ChangeServerTypeAction(val index: Int): StartUIAction()
    data class SeeInstructionsClickAction(val url: String): StartUIAction()
    data object FinishClickAction: StartUIAction()
    data class ApplyIDClickAction(val id: String): StartUIAction()
}