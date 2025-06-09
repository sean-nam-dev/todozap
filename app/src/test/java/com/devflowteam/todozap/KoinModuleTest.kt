package com.devflowteam.todozap

import android.content.Context
import com.devflowteam.domain.repository.ToDoRepository
import com.devflowteam.domain.usecase.settings.ChangeDarkModeUseCase
import com.devflowteam.domain.usecase.settings.ChangeFirstLaunchUseCase
import com.devflowteam.domain.usecase.settings.ChangeIDUseCase
import com.devflowteam.domain.usecase.settings.ChangeServerUseCase
import com.devflowteam.domain.usecase.local.todo.DeleteToDoUseCase
import com.devflowteam.domain.usecase.local.todo.GetAllToDoUseCase
import com.devflowteam.domain.usecase.settings.GetDarkModeUseCase
import com.devflowteam.domain.usecase.settings.GetIDUseCase
import com.devflowteam.domain.usecase.settings.GetServerUseCase
import com.devflowteam.domain.usecase.local.todo.UpsertToDoUseCase
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
        val useCase: UpsertToDoUseCase = get()
        assertNotNull(useCase, "UpsertToDoUseCase should not be null")
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

    @Test
    fun `test GetServerUseCase injection`() {
        val useCase: GetServerUseCase = get()
        assertNotNull(useCase, "  should not be null")
    }

    @Test
    fun `test ChangeServerUseCase injection`() {
        val useCase: ChangeServerUseCase = get()
        assertNotNull(useCase, "  should not be null")
    }

    @Test
    fun `test GetIDUseCase injection`() {
        val useCase: GetIDUseCase = get()
        assertNotNull(useCase, "  should not be null")
    }

    @Test
    fun `test ChangeIDUseCase injection`() {
        val useCase: ChangeIDUseCase = get()
        assertNotNull(useCase, "  should not be null")
    }

    @Test
    fun `test GetDarkModeUseCase injection`() {
        val useCase: GetDarkModeUseCase = get()
        assertNotNull(useCase, "  should not be null")
    }

    @Test
    fun `test ChangeDarkModeUseCase injection`() {
        val useCase: ChangeDarkModeUseCase = get()
        assertNotNull(useCase, "  should not be null")
    }

    @Test
    fun `test GetFirstLaunchUseCase injection`() {
        val useCase: GetFirstLaunchUseCase = get()
        assertNotNull(useCase, "  should not be null")
    }

    @Test
    fun `test ChangeFirstLaunchUseCase injection`() {
        val useCase: ChangeFirstLaunchUseCase = get()
        assertNotNull(useCase, "  should not be null")
    }
}