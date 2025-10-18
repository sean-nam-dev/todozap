package com.devflowteam.domain.usecase

import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.Result
import com.devflowteam.core.utils.Action
import com.devflowteam.domain.usecase.local.todo.GetAllToDoUseCase
import com.devflowteam.domain.usecase.local.todo.SearchToDoUseCase
import com.devflowteam.domain.usecase.local.todosync.DeleteAllToDoSyncActionUseCase
import com.devflowteam.domain.usecase.local.todosync.DeleteToDoSyncActionUseCase
import com.devflowteam.domain.usecase.local.todosync.GetAllToDoSyncActionUseCase
import com.devflowteam.domain.usecase.network.NetworkMonitorReadUseCase
import com.devflowteam.domain.usecase.network.NetworkMonitorUnregisterUseCase
import com.devflowteam.domain.usecase.remote.GetAllTasksUseCase
import com.devflowteam.domain.usecase.remote.UpsertTaskUseCase
import com.devflowteam.domain.usecase.settings.ChangeHardSyncUseCase
import com.devflowteam.domain.usecase.settings.GetHardSyncUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach

class SyncManagerUseCase(
    private val networkMonitorReadUseCase: NetworkMonitorReadUseCase,
    private val networkMonitorUnregisterUseCase: NetworkMonitorUnregisterUseCase,
    private val getAllToDoSyncActionUseCase: GetAllToDoSyncActionUseCase,
    private val searchToDoUseCase: SearchToDoUseCase,
    private val upsertTaskUseCase: UpsertTaskUseCase,
    private val deleteTaskUseCase: UpsertTaskUseCase,
    private val deleteToDoSyncActionUseCase: DeleteToDoSyncActionUseCase,
    private val changeHardSyncUseCase: ChangeHardSyncUseCase,
    private val readHardSyncUseCase: GetHardSyncUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val deleteAllToDoSyncActionUseCase: DeleteAllToDoSyncActionUseCase,
    private val getAllToDoUseCase: GetAllToDoUseCase
) {
    suspend operator fun invoke() {
        networkMonitorReadUseCase()
            .filter { it }
            .collectLatest { handleSync() }
    }

    private suspend fun handleSync() {
        val getAllToDoSyncResult = getAllToDoSyncActionUseCase()

        if (getAllToDoSyncResult is Result.Success) {
            getAllToDoSyncResult.data.collect { list ->
                list.forEach { item ->
                    val todoResult = searchToDoUseCase(item.toDoId)
                    if (todoResult !is Result.Success) return@forEach

                    val isSuccess = retryWithLimit(5) {
                        when (item.action) {
                            Action.UPSERT -> upsertTaskUseCase(todoResult.data)
                            Action.DELETE -> deleteTaskUseCase(todoResult.data)
                        }
                    }

                    if (isSuccess) deleteToDoSyncActionUseCase(item)
                    else changeHardSyncUseCase(true)
                }
            }
        }

        if (readHardSyncUseCase().first()) handleHardSync()

        networkMonitorUnregisterUseCase()
    }

    private suspend fun handleHardSync() {
        var isSuccess = true
        var successTaskCounter = 0

        val allTasksResult = getAllTasksUseCase()
        if (allTasksResult !is Result.Success) return

        val deleteAllToDoSyncActionResult = deleteAllToDoSyncActionUseCase()
        if (deleteAllToDoSyncActionResult !is Result.Success) return

        allTasksResult.data.forEach { item ->
            val deleteTaskResult = deleteTaskUseCase(item)
            if (deleteTaskResult !is Result.Success) isSuccess = false
        }

        val getAllToDoResult = getAllToDoUseCase()
        if (getAllToDoResult !is Result.Success) return

        getAllToDoResult.data.first().forEach { item ->
            val upsertTaskResult = upsertTaskUseCase(item)
            if (upsertTaskResult is Result.Success) successTaskCounter++
        }

        if (getAllToDoResult.data.first().size == successTaskCounter && isSuccess) {
            changeHardSyncUseCase(false)
        }
    }

    private suspend fun retryWithLimit(
        times: Int,
        block: suspend () -> Result<Unit, DataError.Network>
    ): Boolean {
        repeat(times) {
            val result = block()
            if (result is Result.Success) return true
        }
        return false
    }
}