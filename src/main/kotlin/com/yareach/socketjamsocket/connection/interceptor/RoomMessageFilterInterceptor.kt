package com.yareach.socketjamsocket.connection.interceptor

import ch.qos.logback.core.status.Status
import com.yareach.socketjamsocket.common.extensions.throwErrorMessage
import com.yareach.socketjamsocket.connection.repository.SocketConnectionRepository
import org.springframework.http.HttpStatus
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class RoomMessageFilterInterceptor(
    private val socketConnectionRepository: SocketConnectionRepository,
): ChannelInterceptor {
    val subscribeTopicRegex: Regex by lazy {
        Regex("""/([a-zA-Z0-9])+/rooms/(?<roomId>\w+)(/[a-zA-Z0-9]*)*""")
    }

    private fun needCheck(accessor: StompHeaderAccessor): Boolean {
        val destination = accessor.destination ?: return false

        return subscribeTopicRegex.matches(destination)
    }

    private fun filter(accessor: StompHeaderAccessor): Boolean {
        val destination = accessor.destination ?: return false
        val sessionId = accessor.sessionId ?: return false

        val connection = socketConnectionRepository.findById(sessionId)
            ?: throwErrorMessage(ResponseStatusException(HttpStatus.CONFLICT, "can't get connection by sessionId $sessionId"))

        val matchedRoomId = subscribeTopicRegex.find(destination)?.groups?.get("roomId")?.value ?: return false
        val roomId = connection.roomId

        val userJoinedThisRoom = roomId == matchedRoomId
        if(!userJoinedThisRoom) {
            throwErrorMessage(ResponseStatusException(HttpStatus.UNAUTHORIZED, "User can send messages only to rooms they have joined, but the user is not in room $matchedRoomId"))
        }
        return true
    }

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java) ?: return null

        val needCheck = needCheck(accessor)

        if(needCheck) {
            println(filter(accessor))
        }

        return message
    }
}