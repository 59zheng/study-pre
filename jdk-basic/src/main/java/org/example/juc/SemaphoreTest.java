package org.example.juc;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.juc.ThreadPollTest;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author yanzheng
 * @Date 2023/7/10 09:42
 */
@Slf4j
public class SemaphoreTest {

    /**
     * 信号量主要用于两个目的:
     * <p>
     * 用于多个共享资源的互斥使用.   (区别于 sync 和 reentrantLock 只是锁定一个线程访问资源,这个可以锁定多个线程同时访问)
     * 用于并发线程数的控制.    (业务线程池,不过的子业务规定最大的使用线程数量. 类似于countDownLatch 但是 countDownLatch 不能重复使用,这个可以)
     * acquire ()  release() 配合使用 尝试获取资源,和释放资源
     * 默认情况下,Semaphore采用的是非公平性调度策略.
     * <p>
     * 如果Semaphore构造器中的参数permits值设置为1,所创建的Semaphore相当于一个互斥锁.与其他互斥锁不同的是,这种互斥锁允许一个线程释放另外一个线程所持有的锁.因为一个线程可以在未执行过Semaphore.acquire()的情况下执行相应的Semaphore.release().
     */

    public static void semaphoreTest(ThreadPoolExecutor threadPoolExecutor) {
        // 这段业务代码 允许同时几个线程访问
        // 使用场景
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                try {
                    // 尝试获取线程资源
                    semaphore.acquire();
                    log.info("线程{} 抢占成功,正在执行", finalI);
                    Thread.sleep(RandomUtil.randomInt(1000, 5000));
                    log.info("线程{} 执行完成", finalI);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    // 释放资源
                    semaphore.release();
                }

            });
        }
        try {
            threadPoolExecutor.shutdown();
        } catch (Exception e) {
            log.error(" threadPoolExecutor.shutdown  abort");
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {
        ThreadPollTest threadPollTest = new ThreadPollTest();
        ThreadPoolExecutor threadPoolExecutor1 = threadPollTest.initPoll();
        semaphoreTest(threadPoolExecutor1);

    }


}
