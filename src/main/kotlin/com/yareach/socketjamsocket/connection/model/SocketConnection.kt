package com.yareach.socketjamsocket.connection.model

import com.yareach.socketjamcommon.user.model.UserIdentify
import com.yareach.socketjamsocket.connection.entity.SocketConnectionEntity

class SocketConnection(
    val sessionId: String,
    val userId: UserIdentify,
    val subscribedRoomId: String? = null
) {
    companion object {
        fun fromEntity(entity: SocketConnectionEntity) = SocketConnection(
            sessionId = entity.sessionId,
            userId = entity.userId,
            subscribedRoomId = entity.subscribedRoomId
        )
    }

    fun toEntity() = SocketConnectionEntity(
        sessionId = sessionId,
        userId = userId,
        subscribedRoomId = subscribedRoomId
    )
}