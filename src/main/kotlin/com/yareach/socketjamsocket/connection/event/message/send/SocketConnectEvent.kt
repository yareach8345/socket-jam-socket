package com.yareach.socketjamsocket.connection.event.message.send

import com.yareach.socketjamcommon.types.UserId
import com.yareach.socketjamsocket.connection.model.SocketConnection

data class SocketConnectEvent(
    val socketSessionId: String,
    val userId: UserId,
    val roomId: String,
)

fun SocketConnectEvent(socketConnection: SocketConnection) = SocketConnectEvent(
    socketSessionId = socketConnection.sessionId,
    userId = socketConnection.userId,
    roomId = socketConnection.roomId
)