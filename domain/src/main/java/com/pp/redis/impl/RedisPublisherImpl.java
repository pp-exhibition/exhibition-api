package com.pp.redis.impl;

import com.pp.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisherImpl implements RedisPublisher {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void publish(final String channel, final String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }

}
