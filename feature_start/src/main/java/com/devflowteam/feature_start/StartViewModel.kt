package com.devflowteam.feature_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devflowteam.domain.usecase.ChangeFirstLaunchUseCase
import com.devflowteam.domain.usecase.ChangeIDUseCase
import com.devflowteam.domain.usecase.ChangeServerUseCase
import com.devflowteam.domain.usecase.CheckUserUseCase
import com.devflowteam.domain.usecase.OpenWebsiteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.devflowteam.core.common.Result
import java.util.UUID

class StartViewModel(
    private val openWebsiteUseCase: OpenWebsiteUseCase,
    private val checkUserUseCase: CheckUserUseCase,
    private val changeServerUseCase: ChangeServerUseCase,
    private val changeIDUseCase: ChangeIDUseCase,
    private val isFirstLaunchUseCase: ChangeFirstLaunchUseCase
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
                    changeServerUseCase(state.value.serverLink)
                    changeIDUseCase(state.value.id)

                    isFirstLaunchUseCase(false)

                    _oneTimeEvents.emit(Events.NavigateToHome)
                }
            }
            is StartUIAction.ApplyIDClickAction -> {
                viewModelScope.launch {
                    if (isValidUUID(action.id)) {
                        when (checkUserUseCase(action.id)) {
                            is Result.Error -> {
                                _oneTimeEvents.emit(Events.ShowToastInvalidIdError)
                            }
                            is Result.Success -> {
                                _state.update { it.copy(id = action.id) }
                                _oneTimeEvents.emit(Events.ShowToastSuccessSet)
                            }
                        }
                    } else {
                        _oneTimeEvents.emit(Events.ShowToastInvalidIdError)
                    }
                }
            }
            is StartUIAction.SeeInstructionsClickAction -> {
                openWebsiteUseCase.invoke(action.url)
            }
        }
    }

    sealed class Events {
        data object NavigateToHome: Events()
        data object ShowToastInvalidIdError: Events()
        data object ShowToastSuccessSet: Events()
    }

    private fun isValidUUID(id: String): Boolean {
        return try {
            UUID.fromString(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}