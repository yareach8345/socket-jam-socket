package com.yareach.socketjamsocket.config.socket

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val applicationContext: ApplicationContext
): WebSocketMessageBrokerConfigurer{
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/app")
        registry.enableSimpleBroker("/topic")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry
            .addEndpoint("/ws-stomp")
            .setAllowedOriginPatterns("*")
            .withSockJS()
        registry.setErrorHandler(StompErrorHandler())
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        val interceptors = applicationContext.getBeansOfType(ChannelInterceptor::class.java).values
        for(interceptor in interceptors) {
            registration.interceptors(interceptor)
        }
    }
}