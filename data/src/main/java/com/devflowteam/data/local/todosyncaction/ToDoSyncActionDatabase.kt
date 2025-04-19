package com.devflowteam.data.local.todosyncaction

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ToDoSyncActionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ToDoSyncActionDatabase: RoomDatabase() {
    abstract val dao: ToDoSyncActionDao
}