package com.yareach.socketjamsocket.connection.repository

import com.yareach.socketjamsocket.connection.model.SocketConnection

interface SocketConnectionRepository {
    fun save(socketConnection: SocketConnection): SocketConnection

    fun findById(sessionId: String): SocketConnection?

    fun deleteById(sessionId: String)
}