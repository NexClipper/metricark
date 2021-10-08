package com.nexcloud.api.thread;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class WithoutSynchronizingMultiThreadTest {

    // 실패케이스
    // 동기화 처리 안 했을 떄 잘못된 값을 가져오는 것을 확인

    private static final int TRIAL = 10;
    private final Map<String, String> tokenCache = new ConcurrentHashMap<>();
    private int counterA = 0;
    private int counterB = 0;

    @DisplayName("동기화 미 처리시 부정확한 값을 획득하는 경우가 발생하는 것을 확인한다")
    @Test
    public void multiThreadJobWithoutSynchronizingTest() throws InterruptedException {
        //given //when
        executeMultiThreadJob();
        TimeUnit.SECONDS.sleep(5);

        //then
        assertThat(counterA).isNotEqualTo(TRIAL);
        assertThat(counterB).isNotEqualTo(TRIAL);
    }

    private void methodA() throws InterruptedException {
        tokenCache.put("key", "A");
        TimeUnit.MILLISECONDS.sleep(100);
        if (tokenCache.get("key").equals("A")) {
            counterA++;
        }
        System.out.println("method A result: " + tokenCache.get("key"));
    }

    private void methodB() throws InterruptedException {
        tokenCache.put("key", "B");
        TimeUnit.MILLISECONDS.sleep(100);
        if (tokenCache.get("key").equals("B")) {
            counterB++;
        }
        System.out.println("method B result: " + tokenCache.get("key"));
    }

    public void executeMultiThreadJob() {
        new Thread(() -> {
            for (int i = 0; i < TRIAL; i++) {
                try {
                    methodA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < TRIAL; i++) {
                try {
                    methodB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
