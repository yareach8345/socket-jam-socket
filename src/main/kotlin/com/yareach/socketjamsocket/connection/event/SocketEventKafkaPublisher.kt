package com.yareach.socketjamsocket.connection.event

import com.yareach.socketjamsocket.connection.event.message.send.SocketConnectEvent
import com.yareach.socketjamsocket.connection.event.message.send.SocketDisconnectEvent
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Component

@Component
class SocketEventKafkaPublisher(
    private val streamBridge: StreamBridge
): SocketEventPublisher {
    override fun publishSocketConnectEvent(event: SocketConnectEvent) {
        streamBridge.send(
            "socketConnectedEvent-out-0",
            event
        )
    }

    override fun publishSocketDisconnectEvent(event: SocketDisconnectEvent) {
        streamBridge.send(
            "socketDisconnectedEvent-out-0",
            event
        )
    }
}