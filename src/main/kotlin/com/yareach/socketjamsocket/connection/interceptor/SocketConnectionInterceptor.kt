package com.yareach.socketjamsocket.connection.interceptor

import com.yareach.socketjamcommon.security.domain.TokenDecoder
import com.yareach.socketjamsocket.common.extensions.throwErrorMessage
import com.yareach.socketjamsocket.connection.service.SocketConnectionService
import org.springframework.http.HttpStatus
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageDeliveryException
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class SocketConnectionInterceptor(
    private val socketConnectionService: SocketConnectionService,
    private val tokenDecoder: TokenDecoder,
): ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java) ?: return null
        val type = accessor.messageType

        if(type == SimpMessageType.CONNECT) {
            val authHeader = accessor.getNativeHeader("authHeader")
                ?.get(0)
                ?.takeIf { it.startsWith("Bearer ") }
                ?.let { it.split(" ")[1] }
                ?: throwErrorMessage(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Auth header is required for connect"))

            val sessionId = accessor.sessionId
                ?: throwErrorMessage("Unknown Error.. can't get session id from accessor [when connect]")

            val userId = tokenDecoder.getUserId(authHeader)

            try {
                socketConnectionService.processConnect(sessionId, userId)
            } catch (e: Exception) {
                throwErrorMessage(e)
            }
        } else if (type == SimpMessageType.DISCONNECT) {
            val sessionId = accessor.sessionId
                ?: throwErrorMessage("Unknown Error.. can't get session id from accessor [when disconnect]")

            socketConnectionService.processDisconnect(sessionId)
        }

        return message
    }
}