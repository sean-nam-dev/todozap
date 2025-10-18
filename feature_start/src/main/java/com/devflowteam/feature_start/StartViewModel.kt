package com.devflowteam.feature_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.devflowteam.core.common.Result
import com.devflowteam.domain.usecase.MigrateServerUseCase
import com.devflowteam.domain.usecase.remote.AddUserUseCase
import com.devflowteam.domain.usecase.remote.CheckUserUseCase
import com.devflowteam.domain.usecase.settings.ChangeFirstLaunchUseCase
import com.devflowteam.domain.usecase.settings.ChangeIDUseCase

class StartViewModel(
    private val migrateServerUseCase: MigrateServerUseCase,
    private val checkUserUseCase: CheckUserUseCase,
    private val changeIDUseCase: ChangeIDUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val changeFirstLaunchUseCase: ChangeFirstLaunchUseCase
): ViewModel() {
    private val _state = MutableStateFlow(StartUIState())
    val state: StateFlow<StartUIState> = _state

    private val _oneTimeEvents = MutableSharedFlow<Events>()
    val oneTimeEvents: SharedFlow<Events> = _oneTimeEvents

    fun onStartUIAction(action: StartUIAction) {
        when (action) {
            is StartUIAction.ChangeServerType -> {
                _state.update { it.copy(isDefaultServer = action.index != 0) }
            }
            is StartUIAction.DoneServerChangeClickListener -> {
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
            is StartUIAction.SeeInstructions -> {
                viewModelScope.launch {
                    _oneTimeEvents.emit(Events.OpenWebsite(action.url))
                }
            }
            is StartUIAction.RestoreAccountClickListener -> {
                viewModelScope.launch {
                    _oneTimeEvents.emit(Events.OpenDialog)
                }
            }
            is StartUIAction.ApplyClickListener -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }

                    when (checkUserUseCase(action.id)) {
                        is Result.Error -> {
                            _oneTimeEvents.emit(Events.ShowToast(action.errorMessage))
                        }
                        is Result.Success -> {
                            changeIDUseCase(action.id)
                            _oneTimeEvents.emit(Events.CloseDialog)
                            _oneTimeEvents.emit(Events.ShowToast(action.successMessage))
                        }
                    }

                    _state.update { it.copy(isLoading = false) }
                }
            }
            is StartUIAction.FinishClickListener -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }

                    when (addUserUseCase()) {
                        is Result.Error -> {
                            _oneTimeEvents.emit(Events.ShowToast(action.errorMessage))
                        }
                        is Result.Success -> {
                            changeFirstLaunchUseCase(false)
                            _oneTimeEvents.emit(Events.NavigateToHome)
                        }
                    }

                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    sealed class Events {
        data class ShowToast(val message: String): Events()
        data class OpenWebsite(val link: String): Events()
        data object OpenDialog: Events()
        data object CloseDialog: Events()
        data object NavigateToHome: Events()
    }
}