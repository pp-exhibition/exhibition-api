package com.pp.handler.stomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class StompConnectionHandler {

    private final Set<String> sessions = ConcurrentHashMap.newKeySet();

    @EventListener
    public void handleConnect(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        sessions.add(accessor.getSessionId());
        log(accessor);
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        sessions.remove(accessor.getSessionId());
        log(accessor);
    }

    private void log(final StompHeaderAccessor accessor) {
        log.info(System.lineSeparator() +
                String.format("\t[CONNECTED]\t\t\t\t%s", accessor.getCommand()) +
                System.lineSeparator() +
                String.format("\t[SESSION ID]\t\t\t%s", accessor.getSessionId()) +
                System.lineSeparator() +
                String.format("\t[TOTAL SESSION]\t\t\t%s", sessions.size())
        );
    }

}
