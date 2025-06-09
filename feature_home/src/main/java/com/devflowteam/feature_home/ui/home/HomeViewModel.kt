package com.devflowteam.feature_home.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devflowteam.core.common.Result
import com.devflowteam.core.utils.Action
import com.devflowteam.data.local.toSyncAction
import com.devflowteam.domain.model.Status
import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.usecase.local.todo.DeleteToDoUseCase
import com.devflowteam.domain.usecase.local.todo.GetAllToDoUseCase
import com.devflowteam.domain.usecase.local.todosync.UpsertToDoSyncActionUseCase
import com.devflowteam.domain.usecase.local.todo.UpsertToDoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllToDoUseCase: GetAllToDoUseCase,
    private val upsertToDoUseCase: UpsertToDoUseCase,
    private val deleteToDoUseCase: DeleteToDoUseCase,
    private val upsertToDoSyncActionUseCase: UpsertToDoSyncActionUseCase
): ViewModel() {

    val list: StateFlow<List<ToDo>> = when (val result = getAllToDoUseCase()) {
        is Result.Error -> MutableStateFlow(emptyList())
        is Result.Success -> result.data.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    private val _oneTimeEvents = MutableSharedFlow<Events>()
    val oneTimeEvents: SharedFlow<Events> = _oneTimeEvents

    fun onHomeUIAction(action: HomeUIAction) {
        when (action) {
            is HomeUIAction.CompleteClickAction -> {
                viewModelScope.launch {
                    upsertToDoUseCase(action.toDo.copy(status = Status.COMPLETE))
                    _oneTimeEvents.emit(Events.NavigateBack)
                }
            }
            is HomeUIAction.DeleteClickAction -> {
                viewModelScope.launch {
                    deleteToDoUseCase(action.toDo)
                    upsertToDoSyncActionUseCase(action.toDo.toSyncAction(Action.DELETE))
                    _oneTimeEvents.emit(Events.NavigateBack)
                }
            }
            is HomeUIAction.PassID -> {
                viewModelScope.launch {
                    _oneTimeEvents.emit(Events.NavigateToDetail(action.id, action.isPending))
                }
            }
        }
    }

    sealed class Events {
        data object NavigateBack : Events()
        data class NavigateToDetail(
            val id: Int,
            val isPending: Boolean
        ) : Events()
    }
}