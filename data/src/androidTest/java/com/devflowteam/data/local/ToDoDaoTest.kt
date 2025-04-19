package com.devflowteam.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devflowteam.data.local.todo.ToDoDao
import com.devflowteam.data.local.todo.ToDoDatabase
import com.devflowteam.data.local.todo.ToDoEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ToDoDaoTest {

    private lateinit var database: ToDoDatabase
    private lateinit var dao: ToDoDao

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.dao
    }

    @After
    fun closeDB() {
        database.close()
    }

    @Test
    fun insert_todo_and_get_by_id() = runTest {
        val todo1 = ToDoEntity(
            title = "Cooking",
            text = "Do some groceries and visit a local food festival"
        )
        val todo2 = ToDoEntity(
            title = "Get a license",
            text = "Go to closest office and get a plate"
        )

        dao.insert(todo1)
        dao.insert(todo2)

        val todos = dao.getAll().first()

        assertThat(todos.count()).isEqualTo(2)
        assertThat(todos.last().title).isEqualTo("Get a license")
        assertThat(todos.first().title).isEqualTo("Cooking")
    }
}