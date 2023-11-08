package org.example.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author yanzheng
 * @Date 2023/7/11 10:56
 */
public class AtomicIntegerExample {

    private AtomicInteger atomicInteger = new AtomicInteger();


    public void add() {
        // 递增返回增加之后的值
        atomicInteger.incrementAndGet();
    }

}
