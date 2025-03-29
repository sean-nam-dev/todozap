package com.devflowteam.todozap

import android.content.Context
import com.devflowteam.domain.repository.ToDoRepository
import com.devflowteam.domain.usecase.DeleteToDoUseCase
import com.devflowteam.domain.usecase.GetAllToDoUseCase
import com.devflowteam.domain.usecase.InsertToDoUseCase
import com.devflowteam.todozap.di.appModule
import com.devflowteam.todozap.di.dataModule
import com.devflowteam.todozap.di.domainModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.Mockito.mock
import kotlin.test.assertNotNull

@RunWith(JUnit4::class)
class KoinModuleTest: KoinTest {
    private val moduleList = listOf(appModule, dataModule, domainModule)

    @Before
    fun setup() {
        startKoin {
            androidContext(mock(Context::class.java))
            modules(moduleList)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test ToDoRepository injection`() {
        val repository: ToDoRepository = get()
        assertNotNull(repository, "ToDoRepository should not be null")
    }

    @Test
    fun `test InsertToDoUseCase injection`() {
        val useCase: InsertToDoUseCase = get()
        assertNotNull(useCase, "InsertToDoUseCase should not be null")
    }

    @Test
    fun `test GetAllToDoUseCase injection`() {
        val useCase: GetAllToDoUseCase = get()
        assertNotNull(useCase, "GetAllToDoUseCase should not be null")
    }

    @Test
    fun `test DeleteToDoUseCase injection`() {
        val useCase: DeleteToDoUseCase = get()
        assertNotNull(useCase, "DeleteToDoUseCase should not be null")
    }
}