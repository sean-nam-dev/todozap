package com.devflowteam.data.remote

import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.Result
import com.devflowteam.core.utils.Action
import com.devflowteam.domain.usecase.settings.ChangeHardSyncUseCase
import com.devflowteam.domain.usecase.local.todosync.DeleteToDoSyncActionUseCase
import com.devflowteam.domain.usecase.local.todosync.GetAllToDoSyncActionUseCase
import com.devflowteam.domain.usecase.local.todo.SearchToDoUseCase
import com.devflowteam.domain.usecase.remote.UpsertTaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.launch

class SyncManager(
    private val networkMonitor: NetworkMonitor,
    private val getAllToDoSyncActionUseCase: GetAllToDoSyncActionUseCase,
    private val upsertTaskUseCase: UpsertTaskUseCase,
    private val searchToDoUseCase: SearchToDoUseCase,
    private val deleteTaskUseCase: UpsertTaskUseCase,
    private val deleteToDoSyncActionUseCase: DeleteToDoSyncActionUseCase,
    private val changeHardSyncUseCase: ChangeHardSyncUseCase
) {
    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            networkMonitor.isConnected
                .runningReduce { previous, current -> current && !previous }
                .filter { it }
                .collectLatest {
                    handleSync()
                }
        }
    }

    private suspend fun handleSync() {
        val result = getAllToDoSyncActionUseCase()
        if (result !is Result.Success) return

        result.data.collect { list ->
            list.forEach { item ->
                val todoResult = searchToDoUseCase(item.toDoId)
                if (todoResult !is Result.Success) return@forEach

                val success = retryWithLimit(5) {
                    when (item.action) {
                        Action.UPSERT -> upsertTaskUseCase(todoResult.data)
                        Action.DELETE -> deleteTaskUseCase(todoResult.data)
                    }
                }

                if (success) {
                    deleteToDoSyncActionUseCase(item)
                } else {
                    changeHardSyncUseCase(true)
                }
            }
        }
    }

    private suspend fun retryWithLimit(
        times: Int,
        block: suspend () -> Result<Unit, DataError.Network>
    ): Boolean {
        repeat(times) {
            val result = block()
            if (result is Result.Success) return true
            delay(500)
        }
        return false
    }
}