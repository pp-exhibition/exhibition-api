package com.pp.interceptor;

import com.pp.exception.CustomException;
import com.pp.exception.ResponseCode;
import com.pp.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            final String accessToken = jwtProvider.resolveToken(accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION));
            validateToken(accessToken);
        }

        return message;
    }

    private void validateToken(String accessToken) {
        if (!jwtProvider.isValidateToken(accessToken)) {
            throw new CustomException(ResponseCode.INVALID_AUTHENTICATION);
        }
    }

}
