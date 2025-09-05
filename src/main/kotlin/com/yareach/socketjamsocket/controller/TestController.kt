package com.yareach.socketjamsocket.controller

import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@Profile("dev", "test")
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
        val r = restTemplate.getForObject("http://socket-jam-backend/api/ping", String::class.java)
        return "send requested to \"http://socket-jam-backend/api/ping\" -> ping! $r!!"
    }
}