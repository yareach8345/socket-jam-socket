package com.yareach.socketjamsocket.connection.service

import com.yareach.socketjamcommon.service.enum.ServiceHost
import com.yareach.socketjamcommon.user.model.UserIdentify
import com.yareach.socketjamsocket.common.extensions.throwErrorMessage
import com.yareach.socketjamsocket.connection.interceptor.UserJoinInfoDto
import com.yareach.socketjamsocket.connection.model.SocketConnection
import com.yareach.socketjamsocket.connection.repository.SocketConnectionRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException

@Service
class SocketConnectionServiceImpl(
    private val socketConnectionRepository: SocketConnectionRepository,
    private val restTemplate: RestTemplate
): SocketConnectionService {
    override fun processConnect(
        sessionId: String,
        userId: UserIdentify
    ): SocketConnection {
        val joinInfo = restTemplate.getForObject(
            ServiceHost.ROOM.withPath("/internal/v1/users/${userId.userId}/joinInfo"),
            UserJoinInfoDto::class.java
        ) ?: throw RuntimeException("User joinInfo not found")

        val roomId = joinInfo.roomId ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)

        val socketConnection = SocketConnection(
            sessionId = sessionId,
            roomId = roomId,
            userId = userId,
        )
        return socketConnectionRepository.save(socketConnection)
    }

    override fun processDisconnect(sessionId: String) {
        if(socketConnectionRepository.exists(sessionId)) {
            socketConnectionRepository.deleteById(sessionId)
        }
    }
}