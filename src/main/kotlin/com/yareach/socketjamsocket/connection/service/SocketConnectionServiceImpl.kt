package com.yareach.socketjamsocket.connection.service

import com.yareach.socketjamcommon.user.model.UserIdentify
import com.yareach.socketjamsocket.connection.model.SocketConnection
import com.yareach.socketjamsocket.connection.repository.SocketConnectionRepository
import org.springframework.stereotype.Service

@Service
class SocketConnectionServiceImpl(
    private val socketConnectionRepository: SocketConnectionRepository
): SocketConnectionService {
    override fun processConnect(
        sessionId: String,
        userId: UserIdentify
    ): SocketConnection {
        val socketConnection = SocketConnection(
            sessionId = sessionId,
            userId = userId,
        )
        return socketConnectionRepository.save(socketConnection)
    }

    override fun processDisconnect(sessionId: String) {
        socketConnectionRepository.deleteById(sessionId)
    }
}