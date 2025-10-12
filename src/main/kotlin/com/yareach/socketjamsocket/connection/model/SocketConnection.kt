package com.yareach.socketjamsocket.connection.model

import com.yareach.socketjamcommon.user.model.UserIdentify

data class SocketConnection(
    val sessionId: String,
    val userId: UserIdentify,
    val roomId: String
) {
}