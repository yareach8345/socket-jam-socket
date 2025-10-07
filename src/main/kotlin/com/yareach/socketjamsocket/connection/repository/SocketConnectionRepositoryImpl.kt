package com.yareach.socketjamsocket.connection.repository

import com.yareach.socketjamsocket.connection.model.SocketConnection
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
class SocketConnectionRepositoryImpl(
    private val repository: SocketConnectionRedisRepository
): SocketConnectionRepository {
    override fun save(socketConnection: SocketConnection): SocketConnection {
        val entity = socketConnection.toEntity()
        val result = repository.save(entity)
        return SocketConnection.fromEntity(result)
    }

    override fun findById(sessionId: String): SocketConnection? {
        val findResult = repository.findByIdOrNull(sessionId)
        return findResult
            ?.let { SocketConnection.fromEntity(it) }
    }

    override fun deleteById(sessionId: String) {
        if(!repository.existsById(sessionId)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        repository.deleteById(sessionId)
    }
}