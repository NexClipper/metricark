package com.nexcloud.api.thread;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ConcurrentHashMapGetWithSynchronizingTest {

    private static final int TRIAL = 100000;
//    private final Map<String, String> tokenCache = new ConcurrentHashMap<>();
    // HashMap을 사용해도 똑같이 동작
    private final Map<String, String> tokenCache = new HashMap<>();
    private int counter = 0;
    private String marker;

    @DisplayName("ConcurrentHashMap의 put과 get을 동일한 모니터 객체를 사용한 동기화블록으로 감쌌을 때 get메서드가 정확한 값을 획득하는 것을 확인한다")
    @Test
    public void concurrentHashMapGetWithSynchronizingTest() throws InterruptedException {
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
                // 이 메서드를 동기화하면 부정확한 경우 발생하지 않음
                synchronized (tokenCache) {
                    if (tokenCache.get("key").equals(marker)) {
                        counter++;
                    }
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
        assertThat(counter).isEqualTo(TRIAL);
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