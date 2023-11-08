package org.example.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Author yanzheng
 * @Date 2023/7/4 11:49
 */
@Slf4j
public class ThreadPollTest {

    public ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolExecutor initPoll() {
/**
 *  workQuery 类型
 *  SynchronousQueue  无缓冲等待队列, size =0 不存储元素的队列,会直接将任务交给消费者,
 *                    必须等队列中的添加元素被消费后才能继续添加新的元素。使用SynchronousQueue阻塞队列一般要求maximumPoolSizes为无界，避免线程拒绝执行操作。
 *
 * LinkedBlockingQueue  无界 没有大小限制的 缓存等待队列, 当前执行的线程数量达到 corePoolSize 的数据量,剩余元素会等待. LinkedBlockingQueue 可以给定容量大小, 无参则为无限大
 *
 * ArrayBlockingQueue   有界 缓存等待队列, 指定 缓存队列的大小.
 *
 *
 * */
        SynchronousQueue<Runnable> synchronousQueue = new SynchronousQueue<Runnable>();
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);
        ArrayBlockingQueue<Runnable> queue1 = new ArrayBlockingQueue<>(100);

/**
 * 默认线程工厂类型  无法指定 pool name
 * */
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

/**
 * 任务拒接策略
 *
 * CallerRunsPolicy 来电策略 交给调用方的线程同步处理
 * abortPolicy 丢弃任务 并且抛出异常
 * DiscardPolicy  丢弃任务不抛出异常
 * DiscardOldestPolicy  丢弃队列最前面的任务 ,然后重新尝试执行任务
 * */
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor.DiscardPolicy discardPolicy = new ThreadPoolExecutor.DiscardPolicy();
        ThreadPoolExecutor.DiscardOldestPolicy discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();

        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.SECONDS, queue, threadFactory, callerRunsPolicy);

        return threadPoolExecutor;
    }



    public void blockingWaitPool() {


    }

    /**
     * threadPoolExecutor.shutdown()
     */
    public void threadPoolExecutorWait() {
        threadPoolExecutor.execute(() -> {
            try {
                System.out.println("线程1 运行");
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
            }
        });

        threadPoolExecutor.execute(() -> {
            try {
                System.out.println("线程2 运行");
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
            }
        });
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("threadPoolExecutor.awaitTermination  timeout");
        }
    }

    /**
     * CountDownLatch
     */
    public void CountDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        threadPoolExecutor.execute(() -> {
            try {
                System.out.println("线程1 运行");
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                countDownLatch.countDown();
            }
        });

        threadPoolExecutor.execute(() -> {
            try {
                System.out.println("线程2 运行");
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.wait();
        } catch (InterruptedException e) {
            log.error(" countDownLatch.wait  abort");
            throw new RuntimeException(e);
        }


    }


}
