package com.devflowteam.todozap

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.devflowteam.data.remote.ApiService
import com.devflowteam.domain.usecase.ChangeServerUseCase
import com.devflowteam.domain.usecase.GetFirstLaunchUseCase
import com.devflowteam.domain.usecase.GetServerUseCase
import com.devflowteam.feature_start.StartViewModel
import com.devflowteam.todozap.utils.getThemeColor
import com.devflowteam.todozap.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val firstLaunchUseCase: GetFirstLaunchUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostFragment) { v, insets ->
            val innerPadding = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.setPadding(
                innerPadding.left,
                innerPadding.top,
                innerPadding.right,
                innerPadding.bottom
            )
            WindowInsetsCompat.CONSUMED
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
                as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomAppBar.apply {
            menu.children.forEach { menuItem ->
                MenuItemCompat.setIconTintList(
                    menuItem,
                    ColorStateList.valueOf(
                        getThemeColor(com.google.android.material.R.attr.colorOnPrimary)
                    )
                )
            }

            setOnMenuItemClickListener { menuItem ->
                val currentDestination = navController.currentDestination?.id

                when (menuItem.itemId) {
                    R.id.homeFragment -> {
                        val homeGraphId = com.devflowteam.feature_home.R.id.home_graph

                        if (currentDestination != homeGraphId) {
                            navController.navigate(
                                resId = homeGraphId,
                                args = null,
                                navOptions = navOptions {
                                    popUpTo(homeGraphId) {
                                        inclusive = false
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = false
                                }
                            )
                        }

                        true
                    }
                    else -> false
                }
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.devflowteam.feature_start.R.id.startFragment -> {
                    binding.main.setBackgroundColor(getThemeColor(com.google.android.material.R.attr.background))

                    binding.topAppBar.visibility = View.GONE
                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                }
                com.devflowteam.feature_home.R.id.homeFragment ->  { //
                    binding.main.setBackgroundColor(getThemeColor(com.google.android.material.R.attr.colorPrimaryContainer))

                    binding.topAppBar.visibility = View.GONE
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                }
//                R.id.detailFragment, R.id.creationFragment -> {
//                    binding.bottomAppBar.visibility = View.GONE
//                    binding.fab.visibility = View.GONE
//                }
            }
        }

        lifecycleScope.launch {
            setupStartDestination()
        }
    }

    private suspend fun setupStartDestination() {
        val startDestination = if (firstLaunchUseCase().first()) com.devflowteam.feature_start.R.id.start_graph
            else com.devflowteam.feature_home.R.id.home_graph

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(startDestination)
        navController.setGraph(navGraph, null)
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


