package com.devflowteam.presentation.main

sealed class MainUIAction {
    data class NavigateTo(val destination: Int): MainUIAction()
    data class ChangeLanguage(val language: String): MainUIAction()
    data class CopyId(val title: String, val toastMessage: String): MainUIAction()
    data class ContactUs(val toastMessage: String): MainUIAction()
}