package com.yareach.socketjamsocket.connection.event.message.send

import com.yareach.socketjamcommon.types.UserId
import com.yareach.socketjamsocket.connection.model.SocketConnection

data class SocketDisconnectEvent(
    val socketSessionId: String,
    val userId: UserId,
    val roomId: String
)
fun SocketDisconnectEvent(socketConnection: SocketConnection) = SocketDisconnectEvent(
    socketSessionId = socketConnection.sessionId,
    userId = socketConnection.userId,
    roomId = socketConnection.roomId
)
