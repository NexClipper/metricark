package com.nexcloud.api.thread;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ConcurrentHashMapGetWithoutSynchronizingTest {

    // 성공케이스
    // 동기화 블록으로 감싸고, 값을 정확히 가져오는 것을 확인

    private static final int TRIAL = 100000;
    private final Map<String, String> tokenCache = new ConcurrentHashMap<>();
    private int counter = 0;
    private String marker;

    @DisplayName("동기화 처리시 정확한 값을 획득하는 것을 확인한다")
    @Test
    public void multiThreadJobWithSynchronizingTest() throws InterruptedException {
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
//                synchronized (tokenCache) {
                if (tokenCache.get("key").equals(marker)) {
                    counter++;
//                }
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

