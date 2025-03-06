package com.pp.controller.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ObjectMapper objectMapper;
    private final RedisPublisher redisPublisher;

    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, String message) throws Exception {

    }

}
