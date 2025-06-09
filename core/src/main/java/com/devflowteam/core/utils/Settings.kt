package com.devflowteam.core.utils

import java.util.UUID

sealed class Settings <T> (val key: String, val defaultValue: T) {
    data object DarkMode: Settings<Boolean>("dark_mode", false)
    data object FirstLaunch: Settings<Boolean>("first_launch", true)
    data object ID: Settings<String>("uid", UUID.randomUUID().toString())
    data object Server: Settings<String>("server", Links.DEFAULT_SERVER)
    data object HardSync: Settings<Boolean>("hard_sync", false)
    data object Language: Settings<String>("language", "en")
}