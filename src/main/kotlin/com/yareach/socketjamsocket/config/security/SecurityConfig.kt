package com.yareach.socketjamsocket.config.security

import com.yareach.socketjamcommon.config.security.JwtAuthenticationConverter
import com.yareach.socketjamcommon.security.domain.JwtTokenDecoder
import com.yareach.socketjamcommon.security.domain.TokenDecoder
import com.yareach.socketjamsocket.config.security.filter.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocket

@Configuration
@EnableWebSocket
class SecurityConfig(
    tokenDecoder: TokenDecoder,
) {
    private val jwtFilter = JwtFilter(JwtAuthenticationConverter(tokenDecoder))

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain = httpSecurity
        .csrf { it.disable() }
        .cors { it.disable() }
        .formLogin{ it.disable() }
        .httpBasic{ it.disable() }
        .authorizeHttpRequests { it.anyRequest().permitAll() }
        .sessionManagement{ it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        .build()
}