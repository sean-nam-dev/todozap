package com.devflowteam.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devflowteam.domain.usecase.settings.ChangeLanguageUseCase
import com.devflowteam.domain.usecase.settings.GetFirstLaunchUseCase
import com.devflowteam.domain.usecase.settings.GetIDUseCase
import com.devflowteam.domain.usecase.settings.GetServerUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(
    private val changeLanguageUseCase: ChangeLanguageUseCase,
    private val getFirstLaunchUseCase: GetFirstLaunchUseCase,
    private val getIDUseCase: GetIDUseCase
): ViewModel() {

    private val _isFistLaunch = MutableStateFlow<Boolean?>(null)
    val isFirstLaunch: StateFlow<Boolean?> = _isFistLaunch
        .onStart {
            _isFistLaunch.update { getFirstLaunchUseCase() }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )

    private val _oneTimeEvents = MutableSharedFlow<Events>()
    val oneTimeEvents: SharedFlow<Events> = _oneTimeEvents

    fun onMainUIAction(action: MainUIAction) {
        when (action) {
            is MainUIAction.NavigateTo -> {
                viewModelScope.launch {
                    _oneTimeEvents.emit(Events.NavigateTo(action.destination))
                }
            }
            is MainUIAction.ChangeLanguage -> {
                viewModelScope.launch {
                    changeLanguageUseCase(action.language)
                }
            }
            is MainUIAction.ContactUs -> {
                viewModelScope.launch {
                    try {
                        _oneTimeEvents.emit(Events.SendEmail)
                    } catch (_: Exception) {
                        _oneTimeEvents.emit(Events.ShowToast(action.toastMessage))
                    }
                }
            }
            is MainUIAction.CopyId -> {
                viewModelScope.launch {
                    _oneTimeEvents.emit(Events.CopyToClipboard(action.title, getIDUseCase().first()))
                    _oneTimeEvents.emit(Events.ShowToast(action.toastMessage))
                }
            }
        }
    }

    sealed class Events {
        data class NavigateTo(val destination: Int): Events()
        data class ShowToast(val message: String): Events()
        data object SendEmail: Events()
        data class CopyToClipboard(
            val title: String,
            val id: String
        ): Events()
    }
}