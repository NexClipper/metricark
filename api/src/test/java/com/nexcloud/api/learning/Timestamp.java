package com.nexcloud.api.learning;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Timestamp {
    @DisplayName("")
    @Test
    public void test123() {
        //given
        Map<String, Long> urlCache = new ConcurrentHashMap<>();
        //when
        urlCache.put("someurl", System.currentTimeMillis() / 1000);
        //then
        System.out.println(urlCache.get("someurl"));

        if(urlCache.containsKey("someurl")) {
            System.out.println("cached");
        } else {
            System.out.println("not cached");
        }

    }
}
