package com.nexcloud.api.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ThreadTestWithSynchronizing {

    // 성공케이스
    // 동기화 블록으로 감싸고, 값을 정확히 가져오는 것을 확인

    private final Map<String, String> tokenCache = new ConcurrentHashMap<>();

    private void methodA() throws InterruptedException {
        synchronized (tokenCache) {
            tokenCache.put("key", "A");
            TimeUnit.MILLISECONDS.sleep(100);
            System.out.println("method A result: " + tokenCache.get("key"));
        }
    }

    private void methodB() throws InterruptedException {
        synchronized (tokenCache) {
            tokenCache.put("key", "B");
            TimeUnit.MILLISECONDS.sleep(100);
            System.out.println("method B result: " + tokenCache.get("key"));
        }
    }

    public static void main(String[] args) {
        ThreadTestWithSynchronizing threadTestWithSynchronizing = new ThreadTestWithSynchronizing();

        new Thread(() -> {
            for (int i=0; i<10; i++) {
                try {
                    threadTestWithSynchronizing.methodA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            for (int i=0; i<10; i++) {
                try {
                    threadTestWithSynchronizing.methodB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
