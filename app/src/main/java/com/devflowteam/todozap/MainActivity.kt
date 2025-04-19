package com.devflowteam.todozap

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.devflowteam.data.remote.request.TaskRequest
//import com.devflowteam.data.remote.ApiService
//import com.devflowteam.data.remote.request.UserRequest
//import com.devflowteam.domain.model.Status
//import com.devflowteam.data.remote.response.CodeResponse
//import com.devflowteam.data.remote.response.UserResponse
//import com.devflowteam.domain.model.ToDo
//import kotlinx.coroutines.runBlocking
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
//import retrofit2.Response
//import java.util.UUID
//
//class MainActivity : AppCompatActivity(), KoinComponent {
//
//    private val apiService: ApiService by inject()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        runBlocking {
//            val task = ToDo(
//                id = 1,
//                title = "Купить молоко",
//                text = "Для ужина",
//                status = Status.PENDING,
//                date = "2025-04-02"
//            )
//
//            val request = TaskRequest(
//                userId = "550e8400-e29b-41d4-a716-446655440022",
//                taskData = task
//            )
//
//            try {
//                val response: Response<UserResponse> = apiService.addUser(UserRequest(UUID.randomUUID().toString()))
//                if (response.isSuccessful) {
//                    val taskResponse = response.body()
//
//                    when (taskResponse?.code) {
//                        0 -> println("Успех: юзер добавлен")
//                        else -> println("Неизвестная ошибка: код ${taskResponse?.code}")
//                    }
//                } else {
//                    println("Ошибка HTTP: ${response.code()} - ${response.message()}")
//                }
//            } catch (e: Exception) {
//                println("Исключение: ${e.message}")
//            }
//
////            try {
////                val response: Response<CodeResponse> = apiService.addTask(request.toJson())
////                if (response.isSuccessful) {
////                    val taskResponse = response.body()
////                    when (taskResponse?.code) {
////                        0 -> println("Успех: задача добавлена")
////                        3 -> println("Ошибка: не указан user_id или task_data")
////                        4 -> println("Ошибка: некорректная длина user_id")
////                        5 -> println("Ошибка: некорректный JSON в task_data")
////                        else -> println("Неизвестная ошибка: код ${taskResponse?.code}")
////                    }
////                } else {
////                    println("Ошибка HTTP: ${response.code()} - ${response.message()}")
////                }
////            } catch (e: Exception) {
////                println("Исключение: ${e.message}")
////            }
//        }
//    }
//}


