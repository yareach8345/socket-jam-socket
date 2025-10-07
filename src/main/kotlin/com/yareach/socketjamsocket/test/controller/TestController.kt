package com.yareach.socketjamsocket.test.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class TestController(
    val restTemplate: RestTemplate,
) {
    @GetMapping("/")
    fun t(): String {
        return "test 123"
    }

    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }
}