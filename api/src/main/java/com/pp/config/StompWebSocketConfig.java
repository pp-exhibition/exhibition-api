package com.pp.config;

import com.pp.handler.stomp.CustomStompErrorHandler;
import com.pp.interceptor.JwtChannelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final Environment environment;
    private final JwtChannelInterceptor channelInterceptor;
    private final CustomStompErrorHandler stompErrorHandler;

    @Value("${websocket.allowedOrigins}")
    private String allowedOrigins;

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        if (isProdProfile()) {
            registry.addEndpoint("/ws/connect")
//                    .setAllowedOrigins(allowedOrigins)
                    .setAllowedOriginPatterns("*")
                    .withSockJS();
        } else {
            registry.addEndpoint("/ws/connect")
                    .setAllowedOriginPatterns("*");
        }

        registry.setErrorHandler(stompErrorHandler);

    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/chat"); // publish
        registry.enableSimpleBroker("/room"); // subscribe
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(channelInterceptor);
    }

    private boolean isProdProfile() {
        for (String activeProfile : environment.getActiveProfiles()) {
            if (activeProfile.equals("prod")) {
                return true;
            }
        }
        return false;
    }

}
