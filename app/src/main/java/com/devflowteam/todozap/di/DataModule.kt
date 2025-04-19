package com.devflowteam.todozap.di

import androidx.room.Room
import com.devflowteam.core.utils.Settings
import com.devflowteam.data.local.todo.ToDoDao
import com.devflowteam.data.local.todo.ToDoDatabase
import com.devflowteam.data.local.todosyncaction.ToDoSyncActionDao
import com.devflowteam.data.local.todosyncaction.ToDoSyncActionDatabase
import com.devflowteam.data.remote.ApiService
import com.devflowteam.data.repository.ApiServiceRepositoryImpl
import com.devflowteam.data.repository.SettingsRepositoryImpl
import com.devflowteam.data.repository.ToDoRepositoryImpl
import com.devflowteam.data.repository.ToDoSyncActionRepositoryImpl
import com.devflowteam.domain.repository.ApiServiceRepository
import com.devflowteam.domain.repository.SettingsRepository
import com.devflowteam.domain.repository.ToDoRepository
import com.devflowteam.domain.repository.ToDoSyncActionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url

                val settingsRepository: SettingsRepository = get()
                val baseUrl = runBlocking {
                    settingsRepository.read(Settings.Server).first()
                }

                val parsedBaseUrl = baseUrl.toHttpUrl()
                val newUrl = originalUrl.newBuilder()
                    .scheme(parsedBaseUrl.scheme)
                    .host(parsedBaseUrl.host)
                    .port(parsedBaseUrl.port)
                    .build()

                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }
            .addInterceptor { chain ->
                val request = chain.request()
                var response = chain.proceed(request)
                var tryCount = 0

                while (!response.isSuccessful && tryCount < 3) {
                    tryCount++
                    response = chain.proceed(request)
                }

                response
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    single<Retrofit> {
        val client: OkHttpClient = get()

        Retrofit.Builder()
            .baseUrl("http://192.168.0.45/host/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    single<ApiService> {
        val retrofit: Retrofit = get()
        retrofit.create(ApiService::class.java)
    }

    single<ToDoDatabase> {
        Room.databaseBuilder(
            context = get(),
            klass = ToDoDatabase::class.java,
            "todo.db"
        ).build()
    }

    single<ToDoSyncActionDatabase> {
        Room.databaseBuilder(
            context = get(),
            klass = ToDoSyncActionDatabase::class.java,
            "todo_sync_action.db"
        ).build()
    }

    single<ToDoDao> {
        val db: ToDoDatabase = get()
        db.dao
    }

    single<ToDoSyncActionDao> {
        val db: ToDoSyncActionDatabase = get()
        db.dao
    }

    single<ToDoRepository> {
        ToDoRepositoryImpl(
            toDoDb = get(),
            toDoDao = get(),
            toDoSyncActionDao = get()
        )
    }

    single<ToDoSyncActionRepository> {
        ToDoSyncActionRepositoryImpl(
            dao = get()
        )
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(context = get())
    }

    single<ApiServiceRepository> {
        ApiServiceRepositoryImpl(apiService = get())
    }
}