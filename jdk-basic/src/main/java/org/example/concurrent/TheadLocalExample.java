package org.example.concurrent;

/**
 * @Author yanzheng
 * @Date 2023/7/11 11:43
 */
public class TheadLocalExample {
    public static void main(String[] args) {
        ThreadLocal threadLocal = new ThreadLocal();

        Thread thread1 = new Thread(() -> {
            threadLocal.set(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
            threadLocal.remove();
        });

        Thread thread2 = new Thread(() -> {
            threadLocal.set(2);
            threadLocal.remove();
        });

        thread1.start();
        thread2.start();
    }
}