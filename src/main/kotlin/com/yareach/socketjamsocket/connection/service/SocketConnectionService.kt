package com.yareach.socketjamsocket.connection.service

import com.yareach.socketjamsocket.connection.model.SocketConnection
import java.util.UUID

interface SocketConnectionService {
    fun processConnect(
        sessionId: String,
        userId: UUID,
    ): SocketConnection

    fun processDisconnect(
        sessionId: String
    )
}