package org.example.juc;

import java.util.concurrent.Callable;

/**
 * @Author yanzheng
 * @Date 2023/2/1 11:51
 */
public class ThreadNewUtils {


    /**
     * 函数式接口,可直接 匿名内部类
     */
    public void instanceImplementRunnable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("你好");
            }
        }).start();

    }

    /**
     *只能继承 非函数式接口 迷迷瞪瞪糊糊涂涂一天又混过去了奋斗啥啊躺平就好
     */
    public static class CallableDemo implements Callable{


        @Override
        public Object call() throws Exception {
            System.out.println("你好");
            return "你好";
        }
    }
    public void instanceImplementCallable() {
        CallableDemo callableDemo = new CallableDemo();


    }

}
