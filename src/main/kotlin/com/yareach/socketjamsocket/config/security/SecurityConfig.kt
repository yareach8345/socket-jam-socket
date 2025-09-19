package com.yareach.socketjamsocket.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.socket.config.annotation.EnableWebSocket

@Configuration
@EnableWebSocket
class SecurityConfig {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain = httpSecurity
        .csrf { it.disable() }
        .formLogin{ it.disable() }
        .httpBasic{ it.disable() }
        .authorizeHttpRequests { it.anyRequest().permitAll() }
        .sessionManagement{ it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .build()
}