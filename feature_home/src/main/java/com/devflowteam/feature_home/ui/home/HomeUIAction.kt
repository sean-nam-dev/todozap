package com.devflowteam.feature_home.ui.home

import com.devflowteam.domain.model.ToDo

sealed class HomeUIAction {
    data class CompleteClickAction(val toDo: ToDo): HomeUIAction()
    data class DeleteClickAction(val toDo: ToDo): HomeUIAction()
    data class PassID(
        val id: Int,
        val isPending: Boolean
    ): HomeUIAction()
}