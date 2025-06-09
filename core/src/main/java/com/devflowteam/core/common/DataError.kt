package com.devflowteam.core.common

sealed interface DataError {

    enum class Network: Error {
        EXTERNAL,
        UNKNOWN,
        NO_SERVER_AVAILABLE
    }

    enum class Local: Error {
        DISK_FULL,
        UNKNOWN
    }
}

sealed interface Error