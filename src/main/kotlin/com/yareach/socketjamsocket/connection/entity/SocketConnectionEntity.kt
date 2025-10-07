package com.yareach.socketjamsocket.connection.entity

import com.yareach.socketjamcommon.user.model.UserIdentify
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("socket-connection")
class SocketConnectionEntity (
    @Id
    val sessionId: String,
    val userId: UserIdentify,
    val subscribedRoomId: String? = null
) {}