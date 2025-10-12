package com.yareach.socketjamsocket.config.socket

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val interceptors: List<ChannelInterceptor>
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
        for(interceptor in interceptors) {
            registration.interceptors(interceptor)
        }
    }

    override fun configureWebSocketTransport(registry: WebSocketTransportRegistration) {
    }
}