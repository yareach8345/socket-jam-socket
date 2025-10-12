package com.yareach.socketjamsocket.connection.repository

import com.yareach.socketjamsocket.connection.entity.SocketConnectionEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SocketConnectionRedisRepository : CrudRepository<SocketConnectionEntity, String>