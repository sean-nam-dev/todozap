package com.devflowteam.feature_language

sealed class LanguageUIAction {
    data class RadioClickAction(val language: String): LanguageUIAction()
}