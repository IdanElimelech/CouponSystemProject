package com.jb.couponsystem.controller;

import com.jb.couponsystem.model.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Iterator;
import java.util.Map;

@EnableScheduling
public class TokenMapUtils {

    /* 30 Minutes in milliseconds */
    private static final long IDLE_TIME = 1800000;

    private Map<String, ClientSession> tokenMap;

    @Autowired
    public TokenMapUtils(@Qualifier("tokens") Map<String, ClientSession> tokenMap) {
        this.tokenMap = tokenMap;
    }

    /**
     * A Function to remove expired tokens, remove coupons that are no't used longer then (IDLE_TIME)
     */
    @Scheduled(fixedRate = IDLE_TIME)
    public void tokenMapIdleCleaner() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, ClientSession>> iterator = tokenMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ClientSession> next = iterator.next();
            ClientSession clientSession = next.getValue();
            if (currentTime - clientSession.getLastAccessMillis() > IDLE_TIME) {
                iterator.remove();
            }
        }
    }
}
