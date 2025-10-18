package com.devflowteam.feature_server

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devflowteam.core.common.Result
import com.devflowteam.domain.usecase.MigrateServerUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServerViewModel(
    private val migrateServerUseCase: MigrateServerUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ServerUIState())
    val state: StateFlow<ServerUIState> = _state

    private val _oneTimeEvents = MutableSharedFlow<Events>()
    val oneTimeEvents: SharedFlow<Events> = _oneTimeEvents

    fun onServerUIAction(action: ServerUIAction) {
        when (action) {
            is ServerUIAction.DoneServerChangeClickListener -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }

                    when (migrateServerUseCase(action.newServer)) {
                        is Result.Error -> {
                            _oneTimeEvents.emit(Events.ShowToast(action.errorMessage))
                        }
                        is Result.Success -> {
                            _oneTimeEvents.emit(Events.ShowToast(action.successMessage))
                        }
                    }

                    _state.update { it.copy(isLoading = false) }
                }
            }
            is ServerUIAction.SeeInstructionsClickAction -> {
                viewModelScope.launch {
                    _oneTimeEvents.emit(Events.OpenWebsite(action.url))
                }
            }
        }
    }

    sealed class Events {
        data class ShowToast(val message: String): Events()
        data class OpenWebsite(val link: String): Events()
    }
}