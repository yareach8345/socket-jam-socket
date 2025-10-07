package com.yareach.socketjamsocket.config.socket

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class WebSocketEventListener {

    @EventListener
    fun handleWebSocketDisconnect(event: SessionDisconnectEvent) {
        val sessionId = event.sessionId
        val closeStatus = event.closeStatus
        println("세션 $sessionId 끊김, 상태: $closeStatus")

        // 사용자 상태 갱신, 로그 기록, 구독 해제 처리 등
    }

    @EventListener
    fun handleWebSocketConnect(event: SessionConnectEvent) {
        println(event.message.headers)
        val sessionId = event.message.headers["simpSessionId"]
        println("새 세션 연결됨: $sessionId")

        // 사용자 상태 갱신, 로그 기록, 구독 해제 처리 등
    }
}