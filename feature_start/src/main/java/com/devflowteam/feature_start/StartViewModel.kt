package com.devflowteam.feature_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devflowteam.domain.usecase.settings.ChangeFirstLaunchUseCase
import com.devflowteam.domain.usecase.settings.ChangeIDUseCase
import com.devflowteam.domain.usecase.settings.ChangeServerUseCase
import com.devflowteam.domain.usecase.remote.CheckUserUseCase
import com.devflowteam.domain.usecase.OpenWebsiteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.devflowteam.core.common.Result
import com.devflowteam.domain.usecase.remote.AddUserUseCase

class StartViewModel(
    private val openWebsiteUseCase: OpenWebsiteUseCase,
    private val checkUserUseCase: CheckUserUseCase,
    private val changeServerUseCase: ChangeServerUseCase,
    private val changeIDUseCase: ChangeIDUseCase,
    private val isFirstLaunchUseCase: ChangeFirstLaunchUseCase,
    private val addUserUseCase: AddUserUseCase
): ViewModel() {

    private val _state = MutableStateFlow(StartUIState())
    val state: StateFlow<StartUIState> = _state

    private val _oneTimeEvents = MutableSharedFlow<Events>()
    val oneTimeEvents: SharedFlow<Events> = _oneTimeEvents

    fun onStartUIAction(action: StartUIAction) {
        when (action) {
            is StartUIAction.ChangeServerTypeAction -> {
                _state.update { it.copy(isLinkInputVisible = action.index == 1) }
            }
            is StartUIAction.FinishClickAction -> {
                viewModelScope.launch {
                    if (!_state.value.isLoading) {
                        changeServerUseCase(state.value.serverLink)
                        changeIDUseCase(state.value.id)

                        _state.update { it.copy(isLoading = true) }

                        when (addUserUseCase()) {
                            is Result.Error -> {
                                _oneTimeEvents.emit(Events.ShowToastNoServerConnection)
                            }
                            is Result.Success -> {
                                isFirstLaunchUseCase(false)
                                _oneTimeEvents.emit(Events.NavigateToHome)
                            }
                        }

                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
            is StartUIAction.ApplyIDClickAction -> {
                viewModelScope.launch {
                    when (checkUserUseCase(action.id)) {
                        is Result.Error -> {
                            _oneTimeEvents.emit(Events.ShowToastInvalidIdError)
                        }
                        is Result.Success -> {
                            _state.update { it.copy(id = action.id) }
                            _oneTimeEvents.emit(Events.ShowToastSuccessSet)
                        }
                    }
                }
            }
            is StartUIAction.SeeInstructionsClickAction -> {
                openWebsiteUseCase(action.url)
            }
        }
    }

    sealed class Events {
        data object NavigateToHome: Events()
        data object ShowToastInvalidIdError: Events()
        data object ShowToastSuccessSet: Events()
        data object ShowToastNoServerConnection: Events()
    }
}