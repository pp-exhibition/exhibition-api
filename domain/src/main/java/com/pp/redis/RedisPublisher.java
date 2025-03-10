package com.pp.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisPublisher {

    private final StringRedisTemplate stringRedisTemplate;

    public void publish(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }

}
