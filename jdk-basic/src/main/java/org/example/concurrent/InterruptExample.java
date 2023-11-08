package org.example.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author yanzheng
 * @Date 2023/7/11 13:50
 */
@Slf4j
public class InterruptExample {


    private static class MyThread1 extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(20000);
                log.info("sssssssss");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            super.run();
        }
    }

    private static class MyThread2 extends Thread {
        @Override
        public void run() {
            while (!interrupted()){
                log.info("while true always");
            }
        }
    }

    public static void main(String[] args) {
        MyThread2 myThread1 = new MyThread2();
        myThread1.start();
        myThread1.interrupt();

    }

}
