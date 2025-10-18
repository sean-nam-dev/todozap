package com.devflowteam.feature_creation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devflowteam.domain.model.Status
import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.usecase.local.todo.UpsertToDoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreationViewModel(
    private val upsertToDoUseCase: UpsertToDoUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CreationUIState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CreationUIState()
    )

    private val _oneTimeEvents = MutableSharedFlow<Events>()
    val oneTimeEvents: SharedFlow<Events> = _oneTimeEvents

    fun onCreationUIAction(action: CreationUIAction) {
        viewModelScope.launch {
            when (action) {
                is CreationUIAction.NavigateBack -> {
                    _oneTimeEvents.emit(Events.Back)
                }
                is CreationUIAction.OnCalendarClick -> {
                    _oneTimeEvents.emit(Events.OpenCalendar)
                }
                is CreationUIAction.OnSaveClick -> {
                    if (
                        upsertToDoUseCase(
                            ToDo(
                                id = 0,
                                title = action.title,
                                text = action.text,
                                status = Status.PENDING,
                                date = _state.value.date
                            )
                        )
                    ) {
                        _oneTimeEvents.emit(Events.Back)
                    } else {
                        _oneTimeEvents.emit(Events.ShowToast(action.toastErrorText))
                    }
                }
                is CreationUIAction.OnPickerSaveClick -> {
                    _state.update { it.copy(date = action.date) }
                }
            }
        }
    }

    sealed class Events {
        data object Back: Events()
        data object OpenCalendar: Events()
        data class ShowToast(val text: String): Events()
    }
}