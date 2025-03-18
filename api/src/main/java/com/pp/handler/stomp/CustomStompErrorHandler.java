package com.pp.handler.stomp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.exception.CustomException;
import com.pp.exception.ResponseCode;
import com.pp.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomStompErrorHandler extends StompSubProtocolErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        if (ex instanceof MessageDeliveryException mde && mde.getCause() instanceof CustomException customException) {
            return handleCustomException(clientMessage, customException);
        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> handleCustomException(Message<byte[]> clientMessage, CustomException ex) {
        try {
            final ResponseCode responseCode = ex.getResponseCode();
            final String errorMessage = objectMapper.writeValueAsString(CommonResponse.fail(responseCode));

            final StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
            accessor.setMessage(responseCode.getMessage());
            accessor.setLeaveMutable(true);
            accessor.addNativeHeader("code", responseCode.getCode());

            log.error(System.lineSeparator() +
                    String.format("\t[EXCEPTION LOCATION]\t\t%s", ex.getStackTrace()[0]) +
                    System.lineSeparator() +
                    String.format("\t[EXCEPTION TYPE]\t\t\t%s", ex.getClass().getSimpleName()) +
                    System.lineSeparator() +
                    String.format("\t[EXCEPTION CODE]\t\t\t%s", responseCode.getCode()) +
                    System.lineSeparator() +
                    String.format("\t[EXCEPTION MESSAGE]\t\t\t%s", responseCode.getMessage())
            );

            return MessageBuilder.createMessage(errorMessage.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
        } catch (JsonProcessingException e) {
            log.error(System.lineSeparator() +
                    String.format("\t[EXCEPTION LOCATION]\t\t%s", ex.getStackTrace()[0]) +
                    System.lineSeparator() +
                    String.format("\t[EXCEPTION TYPE]\t\t\t%s", ex.getClass().getSimpleName()) +
                    System.lineSeparator() +
                    String.format("\t[EXCEPTION MESSAGE]\t\t\t%s", e.getMessage())
            );
            return super.handleClientMessageProcessingError(clientMessage, ex);
        }
    }

}
