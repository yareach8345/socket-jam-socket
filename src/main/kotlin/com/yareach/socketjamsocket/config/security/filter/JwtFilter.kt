package com.yareach.socketjamsocket.config.security.filter

import com.yareach.socketjamcommon.config.security.JwtAuthenticationConverter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtAuthenticationConverter: JwtAuthenticationConverter
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.let { jwtAuthenticationConverter.convert(it) }

        SecurityContextHolder.getContext().authentication = token

        filterChain.doFilter(request, response)
    }
}