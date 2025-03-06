package com.pp.redis;

public interface RedisPublisher {

    void publish(String channel, String message);

}
