package com.devflowteam.core.common

sealed interface DataError {

    enum class Network: Error {
        UNAVAILABLE,
        NOT_FOUND,
        DOCUMENT_NULL,
        UNKNOWN
    }

    enum class Local: Error {
        DISK_FULL,
        UNKNOWN
    }
}