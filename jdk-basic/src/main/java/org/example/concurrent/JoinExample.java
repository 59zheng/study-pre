package org.example.concurrent;

/**
 * @Author yanzheng
 * @Date 2023/7/11 14:26
 */
public class JoinExample {



    private  class  A extends Thread{
        @Override
        public void run() {
            System.out.println("a");
        }
    }
    private class  B extends  Thread{
        private A a;

        B(A a){
            this.a=a;
        }
        @Override
        public void run() {
            try {
                a.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("B");
        }
    }
    public void test() {
        A a = new A();
        B b = new B(a);
        b.start();
        a.start();
    }

    public static void main(String[] args) {
        JoinExample joinExample = new JoinExample();
        joinExample.test();
    }

}
