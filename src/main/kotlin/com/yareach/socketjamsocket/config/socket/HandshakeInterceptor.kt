package com.yareach.socketjamsocket.config.socket

import org.springframework.http.HttpHeaders
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import java.lang.Exception

class HandshakeInterceptorImpl : HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: Map<String?, Any?>
    ): Boolean {
        //todo : 연결시 필터로 처리되는지 확인하기. 안되면 여기에 직접 구현
        val token = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        println(token)
        println("try handshake")
        return true
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {
        println("end handshake")
    }
}