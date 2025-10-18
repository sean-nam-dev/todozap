package com.devflowteam.feature_creation

sealed class CreationUIAction {
    data object NavigateBack: CreationUIAction()
    data object OnCalendarClick: CreationUIAction()
    data class OnSaveClick(
        val title: String,
        val text: String,
        val toastErrorText: String
    ): CreationUIAction()
    data class OnPickerSaveClick(val date: String): CreationUIAction()
}