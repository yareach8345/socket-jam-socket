package com.yareach.socketjamsocket.config.socket

import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler
import java.nio.charset.StandardCharsets

class StompErrorHandler: StompSubProtocolErrorHandler() {
    override fun handleClientMessageProcessingError(
        clientMessage: Message<ByteArray?>?,
        ex: Throwable
    ): Message<ByteArray?> {
        val errorMessage = ex.message ?: "unknown error"
        return errorMessage(errorMessage)
    }

    private fun errorMessage(errorMessage: String): Message<ByteArray?> {
        val accessor: StompHeaderAccessor = StompHeaderAccessor.create(StompCommand.ERROR)
        accessor.setLeaveMutable(true)

        return MessageBuilder.createMessage(errorMessage.toByteArray(StandardCharsets.UTF_8), accessor.messageHeaders)
    }
}