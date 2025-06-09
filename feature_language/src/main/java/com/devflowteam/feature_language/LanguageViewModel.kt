package com.devflowteam.feature_language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devflowteam.domain.usecase.settings.ChangeLanguageUseCase
import com.devflowteam.domain.usecase.settings.GetLanguageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val getLanguageUseCase: GetLanguageUseCase,
    private val changeLanguageUseCase: ChangeLanguageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LanguageUIState())
    val state: StateFlow<LanguageUIState> = _state

    init {
        viewModelScope.launch {
            _state.update { it.copy(languageCode = getLanguageUseCase().first()) }
        }
    }

    fun onLanguageUIAction(action: LanguageUIAction) {
        when (action) {
            is LanguageUIAction.RadioClickAction -> {
                viewModelScope.launch {
                    changeLanguageUseCase(action.language)
                    _state.update { it.copy(languageCode = action.language) }
                }
            }
        }
    }
}