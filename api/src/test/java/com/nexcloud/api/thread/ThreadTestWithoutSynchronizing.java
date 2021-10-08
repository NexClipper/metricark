package com.nexcloud.api.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ThreadTestWithoutSynchronizing {

    // 실패케이스
    // 동기화 처리 안 했을 떄 잘못된 값을 가져오는 것을 확인

    private final Map<String, String> tokenCache = new ConcurrentHashMap<>();

    private void methodA() throws InterruptedException {
            tokenCache.put("key", "A");
            TimeUnit.MILLISECONDS.sleep(100);
            System.out.println("method A result: " + tokenCache.get("key"));
    }

    private void methodB() throws InterruptedException {
            tokenCache.put("key", "B");
            TimeUnit.MILLISECONDS.sleep(100);
            System.out.println("method B result: " + tokenCache.get("key"));
    }

    public static void main(String[] args) {
        ThreadTestWithoutSynchronizing threadTestWithoutSynchronizing = new ThreadTestWithoutSynchronizing();

        new Thread(() -> {
            for (int i=0; i<10; i++) {
                try {
                    threadTestWithoutSynchronizing.methodA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            for (int i=0; i<10; i++) {
                try {
                    threadTestWithoutSynchronizing.methodB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
