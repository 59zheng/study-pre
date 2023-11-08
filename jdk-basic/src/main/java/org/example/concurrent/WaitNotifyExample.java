package org.example.concurrent;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yanzheng
 * @Date 2023/7/11 14:37
 */
public class WaitNotifyExample {

    public  synchronized  void  before(){
        System.out.println("before");
        notifyAll();
    }
    private Lock lock =new ReentrantLock();
    private Condition condition=lock.newCondition();

    public  void  beforeCondition(){
        lock.lock();
        try {
            System.out.println("before");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public synchronized  void after(){
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("after");
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        WaitNotifyExample waitNotifyExample = new WaitNotifyExample();
        executorService.execute(()->waitNotifyExample.after());
        executorService.execute(()->waitNotifyExample.before());
        executorService.shutdown();
    }

}
