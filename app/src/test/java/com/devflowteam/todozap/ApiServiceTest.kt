package com.devflowteam.todozap

import com.devflowteam.core.common.DataError
import com.devflowteam.core.common.Result
import com.devflowteam.data.remote.ApiService
import com.devflowteam.data.remote.request.TaskRequest
import com.devflowteam.data.remote.request.UserRequest
import com.devflowteam.data.remote.response.CodeResponse
import com.devflowteam.data.remote.response.GetTasksResponse
import com.devflowteam.data.repository.ApiServiceRepositoryImpl
import com.devflowteam.domain.model.Status
import com.devflowteam.domain.model.ToDo
import com.devflowteam.domain.repository.ApiServiceRepository
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ApiServiceTest {

    private lateinit var apiService: ApiService
    private lateinit var apiServiceRepository: ApiServiceRepository

    @Before
    fun setup() {
        apiService = mock(ApiService::class.java)
        apiServiceRepository = ApiServiceRepositoryImpl(apiService)
    }

    @Test
    fun `getAllTasks returns a list of todos`() = runBlocking {
        val response = Response.success(
            GetTasksResponse(
                code = 0,
                tasks = todoList
            )
        )
        `when`(apiService.getAllTasks(USER_ID)).thenReturn(response)

        val result = apiServiceRepository.getAllTasks(USER_ID)
        val tasks = (result as Result.Success).data

        assertEquals(2, tasks.size)
        assertTrue(tasks.first() == todoList.first())
        assertTrue(tasks.last() == todoList.last())
    }

    @Test
    fun `getAllTasks returns error code 3`() = runBlocking {
        val response = Response.success(
            GetTasksResponse(
                code = 3,
                tasks = null
            )
        )
        `when`(apiService.getAllTasks("")).thenReturn(response)

        val result  = apiServiceRepository.getAllTasks("")
        val error = (result as Result.Error).error

        assertTrue(error.name == DataError.Network.EXTERNAL.name)
    }

    @Test
    fun `addUser returns success result`() = runBlocking {
        `when`(apiService.addUser(request)).thenReturn(successCodeResponse)

        val result = apiServiceRepository.addUser(USER_ID)

        assertTrue(result is Result.Success)
    }

    @Test
    fun `addUser returns error result`() = runBlocking {
        `when`(apiService.addUser(request)).thenReturn(errorCodeResponse)

        val result = apiServiceRepository.addUser(USER_ID)

        assertTrue(result is Result.Error)
    }

    @Test
    fun `addTask returns success result`() = runBlocking {
        `when`(apiService.addTask(taskRequestSerialized)).thenReturn(successCodeResponse)

        val result = apiServiceRepository.addTask(
            userId = USER_ID,
            taskData = todoList.first()
        )

        assertTrue(result is Result.Success)
    }

    @Test
    fun `addTask returns error result`() = runBlocking {
        `when`(apiService.addTask(taskRequestSerialized)).thenReturn(errorCodeResponse)

        val result = apiServiceRepository.addTask(
            userId = USER_ID,
            taskData = todoList.first()
        )

        assertTrue(result is Result.Error)
    }

    @Test
    fun `updateTask returns success result`() = runBlocking {
        `when`(apiService.updateTask(taskRequestSerialized)).thenReturn(successCodeResponse)

        val result = apiServiceRepository.updateTask(
            userId = USER_ID,
            taskData = todoList.first()
        )

        assertTrue(result is Result.Success)
    }

    @Test
    fun `updateTask returns error result`() = runBlocking {
        `when`(apiService.updateTask(invalidRequest)).thenReturn(errorCodeResponse)

        val result = apiServiceRepository.updateTask(
            userId = "",
            taskData = todoList.first()
        )

        assertTrue(result is Result.Error)
    }

    @Test
    fun `deleteTask returns success result`() = runBlocking {
        `when`(apiService.deleteTask(taskRequestSerialized)).thenReturn(successCodeResponse)

        val result = apiServiceRepository.deleteTask(
            userId = USER_ID,
            taskData = todoList.first()
        )

        assertTrue(result is Result.Success)
    }

    @Test
    fun `deleteTask returns failure result`() = runBlocking {
        `when`(apiService.deleteTask(invalidRequest)).thenReturn(errorCodeResponse)

        val result = apiServiceRepository.deleteTask(
            userId = USER_ID,
            taskData = todoList.first()
        )

        assertTrue(result is Result.Error)
    }

    companion object {
        private const val USER_ID = "5337350e-82a3-4c0a-8032-4951a8b996a2"
        private val todoList = listOf(
            ToDo(
                id = 1,
                title = "Title #1",
                text = "Here is some text for the first todo",
                status = Status.PENDING,
                date = "2025-01-02"
            ),
            ToDo(
                id = 2,
                title = "Title #2",
                text = "Here is some text for the second todo",
                status = Status.COMPLETE,
                date = "2026-07-11"
            )
        )
        private val request = UserRequest(
            USER_ID
        )
        private val successCodeResponse = Response.success(
            CodeResponse(
                code = 0
            )
        )
        private val errorCodeResponse = Response.error<CodeResponse>(
            404,
            """{"error": "Not Found"}"""
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        private val taskRequestSerialized = TaskRequest(
            userId = USER_ID,
            taskData = todoList.first()
        ).toJson()
        private val invalidRequest = TaskRequest(
            "",
            todoList.first()
        ).toJson()
    }
}