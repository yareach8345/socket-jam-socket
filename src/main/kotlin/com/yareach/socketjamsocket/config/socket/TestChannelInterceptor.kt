package com.yareach.socketjamsocket.config.socket

import com.yareach.socketjamcommon.security.domain.TokenDecoder
import com.yareach.socketjamsocket.connection.service.SocketConnectionService
import org.springframework.http.HttpStatus
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class TestChannelInterceptor(
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
                ?: throw ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "need auth header"
                )
            println(authHeader)

            val sessionId = accessor.sessionId ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "sessionId is null")
            val userIdentify = tokenDecoder.getUserIdentify(authHeader)

            socketConnectionService.processConnect(sessionId, userIdentify)
        } else if (type == SimpMessageType.DISCONNECT) {
            val sessionId = accessor.sessionId ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "sessionId is null")

            socketConnectionService.processDisconnect(sessionId)
        }

        return message
    }
}