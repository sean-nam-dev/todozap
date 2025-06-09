package com.devflowteam.feature_server

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devflowteam.core.common.Result
import com.devflowteam.domain.usecase.MigrateDataUseCase
import com.devflowteam.domain.usecase.OpenWebsiteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServerViewModel(
    private val openWebsiteUseCase: OpenWebsiteUseCase,
    private val migrateDataUseCase: MigrateDataUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ServerUIState())
    val state: StateFlow<ServerUIState> = _state

    private val _oneTimeEvents = MutableSharedFlow<Events>()
    val oneTimeEvents: SharedFlow<Events> = _oneTimeEvents

    fun onServerUIAction(action: ServerUIAction) {
        when (action) {
            is ServerUIAction.OnApplyClick -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }

                    when (migrateDataUseCase(action.serverLink)) {
                        is Result.Error -> {
                            _oneTimeEvents.emit(Events.ShowErrorToast)
                        }
                        is Result.Success -> {
                            _oneTimeEvents.emit(Events.ShowSuccessfulToast)
                        }
                    }

                    _state.update { it.copy(isLoading = false) }
                }
            }
            is ServerUIAction.SeeInstructionsClickAction -> {
                openWebsiteUseCase(action.url)
            }
        }
    }

    sealed class Events {
        data object ShowSuccessfulToast: Events()
        data object ShowErrorToast: Events()
    }
}