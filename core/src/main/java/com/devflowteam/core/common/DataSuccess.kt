package com.devflowteam.core.common

sealed interface DataSuccess {

    enum class Network: Success {
        INSERTED,
        EXISTS
    }
}

sealed interface Success