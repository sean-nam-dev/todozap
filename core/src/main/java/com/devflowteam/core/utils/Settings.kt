package com.devflowteam.core.utils

import java.util.UUID

sealed class Settings <T> (val key: String, val defaultValue: T) {
    data object DarkMode: Settings<Boolean>("dark_mode", false)
    data object FirstLaunch: Settings<Boolean>("first_launch", true)
    data object ID: Settings<String>("uid", UUID.randomUUID().toString())
    data object Server: Settings<String>("server", "https://192.168.0.45/host/")
}