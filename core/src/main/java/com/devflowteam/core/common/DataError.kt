package com.devflowteam.core.common

sealed interface DataError {

    enum class Network: Error {
        EXTERNAL,
        UNKNOWN
    }

    enum class Local: Error {
        DISK_FULL,
        UNKNOWN
    }
}