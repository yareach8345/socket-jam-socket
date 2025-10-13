package com.yareach.socketjamsocket.connection.repository

import com.yareach.socketjamsocket.connection.entity.SocketConnectionEntity
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
        val entity = SocketConnectionEntity.fromModel(socketConnection)
        val result = repository.save(entity)
        return result.toModel()
    }

    override fun exists(sessionId: String): Boolean {
        return repository.existsById(sessionId)
    }

    override fun findById(sessionId: String): SocketConnection? {
        val findResult = repository.findByIdOrNull(sessionId)
        return findResult?.toModel()
    }

    override fun deleteById(sessionId: String) {
        if(!repository.existsById(sessionId)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        repository.deleteById(sessionId)
    }
}