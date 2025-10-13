package com.yareach.socketjamsocket.connection.event

import com.yareach.socketjamsocket.connection.event.message.send.SocketConnectEvent
import com.yareach.socketjamsocket.connection.event.message.send.SocketDisconnectEvent

interface SocketEventPublisher {
    fun publishSocketConnectEvent(event: SocketConnectEvent)

    fun publishSocketDisconnectEvent(event: SocketDisconnectEvent)
}