package org.example.concurrent;

/**
 * @Author yanzheng
 * @Date 2023/7/13 14:59
 */
public class VolatileTest {

    volatile int i;

    public void addI() {
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        final VolatileTest volatileTest = new VolatileTest();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    volatileTest.addI();
                }
            }).start();
        }
        Thread.sleep(10000);//等待10秒，保证上面程序执行完成
        System.out.println(volatileTest.i);
    }
}
