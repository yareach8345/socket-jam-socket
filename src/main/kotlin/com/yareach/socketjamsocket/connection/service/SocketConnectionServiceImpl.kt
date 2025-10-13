package com.yareach.socketjamsocket.connection.service

import com.yareach.socketjamcommon.service.enum.ServiceHost
import com.yareach.socketjamcommon.types.UserId
import com.yareach.socketjamsocket.connection.event.SocketEventPublisher
import com.yareach.socketjamsocket.connection.event.message.send.SocketConnectEvent
import com.yareach.socketjamsocket.connection.event.message.send.SocketDisconnectEvent
import com.yareach.socketjamsocket.connection.model.SocketConnection
import com.yareach.socketjamsocket.connection.repository.SocketConnectionRepository
import com.yareach.socketjamsocket.external.api.room.RoomApiClient
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SocketConnectionServiceImpl(
    private val socketConnectionRepository: SocketConnectionRepository,
    private val socketEventPublisher: SocketEventPublisher,
    private val roomApiClient: RoomApiClient
): SocketConnectionService {
    override fun processConnect(
        sessionId: String,
        userId: UserId
    ): SocketConnection {
        val joinInfo = roomApiClient.getUserJoinInfo(userId)

        val roomId = joinInfo.roomId ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "roomId in userJoinInfo is null")

        val socketConnection = SocketConnection(
            sessionId = sessionId,
            roomId = roomId,
            userId = userId,
        )
        val result = socketConnectionRepository.save(socketConnection)

        val event = SocketConnectEvent(result)
        socketEventPublisher.publishSocketConnectEvent(event)

        return result
    }

    override fun processDisconnect(sessionId: String) {
        val socketConnection = socketConnectionRepository.findById(sessionId)
        if(socketConnection != null) {
            socketConnectionRepository.deleteById(sessionId)

            val event = SocketDisconnectEvent(socketConnection)
            socketEventPublisher.publishSocketDisconnectEvent(event)
        }
    }
}