package com.yareach.socketjamsocket.config.security

import com.yareach.socketjamcommon.resource.utils.ResourceUtil
import com.yareach.socketjamcommon.security.domain.TokenDecoder
import com.yareach.socketjamcommon.security.utils.KeyConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtTokenConfig(
    private val keyConverter: KeyConverter,
    private val resourceUtil: ResourceUtil
) {
    @Bean
    fun jwtTokenDecoder(
        @Value("\${spring.jwt.public-key-file}") path: String,
    ) = resourceUtil.readResourceFile(path)
        .let(keyConverter::stringToPublicKey)
        .let(::TokenDecoder)
}