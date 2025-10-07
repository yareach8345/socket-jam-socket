package com.yareach.socketjamsocket.connection.service

import com.yareach.socketjamcommon.user.model.UserIdentify
import com.yareach.socketjamsocket.connection.model.SocketConnection

interface SocketConnectionService {
    fun processConnect(
        sessionId: String,
        userId: UserIdentify,
    ): SocketConnection

    fun processDisconnect(
        sessionId: String
    )
}