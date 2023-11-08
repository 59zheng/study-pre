package org.example.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author yanzheng
 * @Date 2023/7/11 14:03
 */
public class SynchronizedExample {
    //  ------------------代码块形式: 手动指定锁对象,可以是 this 也可以是自定义的锁 -------------
    public static  class SynchronizedObjectLock implements Runnable {



        @Override
        public void run() {
            // 同步代码块形式——锁为this,两个线程使用的锁是一样的,线程1必须要等到线程0释放了该锁后，才能执行
            synchronized (this) {
                System.out.println("我是线程" + Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "结束");
            }

        }


    }


    //
    public void func1() {
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                System.out.println("sssss" + i);
            }
        }
    }

    public synchronized void func2() {

    }

    public void func() {
        synchronized (SynchronizedExample.class) {
            // ...
        }
    }

    public static void main(String[] args) {
        SynchronizedExample synchronizedExample1 = new SynchronizedExample();
        SynchronizedExample synchronizedExample2 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> synchronizedExample1.func1());
        executorService.execute(() -> synchronizedExample1.func1());
        executorService.shutdown();
    }

}
