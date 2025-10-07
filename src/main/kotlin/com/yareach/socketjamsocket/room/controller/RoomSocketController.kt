package com.yareach.socketjamsocket.room.controller

import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller

@Controller
class RoomSocketController {
    @SubscribeMapping("/test")
    fun initTest(
        @Header("hello") hello: String
    ): String {
        println("hello: $hello")
        println("CONNECTED!")

        return "WOW!!!!"
    }
}