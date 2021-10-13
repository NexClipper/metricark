package com.nexcloud.api.thread;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ConcurrentHashMapGetWithoutSynchronizingTest {

    private static final int TRIAL = 100000;
    private final Map<String, String> tokenCache = new ConcurrentHashMap<>();
    private int counter = 0;
    private String marker;

    @DisplayName("get 혹은 put 메서드를 동기화하지 않는 경우 get메서드가 부정확한 값을 획득하는 경우가 발생하는 것을 확인한다")
    @Test
    public void concurrentHashMapGetWithoutSynchronizingTest() throws InterruptedException {
        //given
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < TRIAL; i++) {
                try {
                    methodA();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < TRIAL; i++) {
                try {
                    methodB();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < TRIAL; i++) {


//                synchronized (tokenCache) {
                    if (tokenCache.get("key").equals(marker)) {
                        counter++;
//                    }
                }
            }
        });

        //when
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(counter);
        assertThat(counter).isNotEqualTo(TRIAL);
    }

    private void methodA() throws InterruptedException {
        synchronized (tokenCache) {
        tokenCache.put("key", "A");
        marker = "A";
        }
    }

    private void methodB() throws InterruptedException {
        synchronized (tokenCache) {
        tokenCache.put("key", "B");
        marker = "B";
    }
    }
}

