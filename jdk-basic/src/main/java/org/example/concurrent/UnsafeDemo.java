package org.example.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author yanzheng
 * @Date 2023/7/10 14:24
 */
public class UnsafeDemo {

    public static void main(String[] args) throws InterruptedException {

        final int threadSize = 1000;
        ThreadUnsafeExample example = new ThreadUnsafeExample();
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadSize; i++) {
            executorService.execute(() -> {
                example.add();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(example.get());
    }

    public static class ThreadUnsafeExample {
              private int cnt = 0;

        public void add() {
            cnt++;
        }

        public int get() {
            return cnt;
        }

    }

}
