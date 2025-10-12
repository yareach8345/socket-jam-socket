package com.yareach.socketjamsocket.connection.entity

import com.yareach.socketjamcommon.user.model.UserIdentify
import com.yareach.socketjamsocket.connection.model.SocketConnection
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("socket-connection", timeToLive = 120)
class SocketConnectionEntity (
    @Id
    val sessionId: String,
    val userId: UserIdentify,
    val roomId: String
) {
    companion object {
        fun fromModel(entity: SocketConnection) = SocketConnectionEntity(
            sessionId = entity.sessionId,
            userId = entity.userId,
            roomId = entity.roomId
        )
    }

    fun toModel() = SocketConnection(
        sessionId = sessionId,
        userId = userId,
        roomId = roomId
    )
}