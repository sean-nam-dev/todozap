package com.devflowteam.core.utils

import java.util.UUID

sealed class Settings <T> (val key: String, val defaultValue: T) {
    data object DarkMode: Settings<Boolean>("dark_mode", false)
    data object FirstLaunch: Settings<Boolean>("first_launch", true)
    data object ID: Settings<String>("uid", UUID.randomUUID().toString())
    data object Server: Settings<String>("server", "http://192.168.0.45/host/") // 192.168.0.45 or 10.0.2.2
    data object HardSync: Settings<Boolean>("hard_sync", false)
    data object Language: Settings<String>("language", "en")
}