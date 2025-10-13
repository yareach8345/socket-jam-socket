package com.yareach.socketjamsocket.connection.model

import java.util.UUID

data class SocketConnection(
    val sessionId: String,
    val userId: UUID,
    val roomId: String
) {
}