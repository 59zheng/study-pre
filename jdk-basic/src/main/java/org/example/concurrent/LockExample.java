package org.example.concurrent;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yanzheng
 * @Date 2023/7/11 14:12
 */
public class LockExample {


    // ---------------------悲观锁调用方式----------------------
    //synchronized



    private Lock lock = new ReentrantLock(true);

    public void func() {
        lock.lock();

        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("sssss" + i);
            }
        } finally {
            // 释放锁
            lock.unlock();

        }
    }

}
