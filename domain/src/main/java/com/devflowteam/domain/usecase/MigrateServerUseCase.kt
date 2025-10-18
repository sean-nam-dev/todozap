package com.devflowteam.domain.usecase

import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.Result
import com.devflowteam.core.utils.Action
import com.devflowteam.core.utils.Links
import com.devflowteam.domain.model.ToDoSyncAction
import com.devflowteam.domain.usecase.local.todo.GetAllToDoUseCase
import com.devflowteam.domain.usecase.local.todosync.DeleteAllToDoSyncActionUseCase
import com.devflowteam.domain.usecase.local.todosync.InsertToDoSyncActionUseCase
import com.devflowteam.domain.usecase.remote.AddUserUseCase
import com.devflowteam.domain.usecase.remote.UpsertTaskUseCase
import com.devflowteam.domain.usecase.settings.ChangeServerUseCase
import kotlinx.coroutines.flow.first
import java.lang.Exception

class MigrateServerUseCase(
    private val changeServerUseCase: ChangeServerUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val getAllToDoUseCase: GetAllToDoUseCase,
    private val upsertTaskUseCase: UpsertTaskUseCase,
    private val deleteAllToDoSyncActionUseCase: DeleteAllToDoSyncActionUseCase,
    private val insertToDoSyncActionUseCase: InsertToDoSyncActionUseCase
) {
    suspend operator fun invoke(newServer: String): Result<Unit, DataError.Network> {
        if (newServer.isBlank()) {
            return Result.Error(DataError.Network.UNKNOWN)
        } else {
            changeServerUseCase(
                Links.HTTP + newServer.replace(" ", "")
            )

            try {
                when (addUserUseCase()) {
                    is Result.Error -> {
                        changeServerUseCase(Links.DEFAULT_SERVER)
                        return Result.Error(DataError.Network.UNKNOWN)
                    }
                    is Result.Success -> {
                        when (val getAllToDoListResult = getAllToDoUseCase()) {
                            is Result.Error -> {
                                changeServerUseCase(Links.DEFAULT_SERVER)
                                return Result.Error(DataError.Network.UNKNOWN)
                            }
                            is Result.Success -> {
                                when (deleteAllToDoSyncActionUseCase()) {
                                    is Result.Error -> {
                                        changeServerUseCase(Links.DEFAULT_SERVER)
                                        return Result.Error(DataError.Network.UNKNOWN)
                                    }
                                    is Result.Success -> {
                                        getAllToDoListResult.data.first().forEach { todo ->
                                            when (upsertTaskUseCase(todo)) {
                                                is Result.Error -> {
                                                    insertToDoSyncActionUseCase(
                                                        ToDoSyncAction(
                                                            id = 0,
                                                            toDoId = todo.id,
                                                            action = Action.UPSERT
                                                        )
                                                    )
                                                }
                                                is Result.Success -> {}
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                changeServerUseCase(Links.DEFAULT_SERVER)
                return Result.Error(DataError.Network.UNKNOWN)
            }

            return Result.Success(Unit)
        }
    }
}