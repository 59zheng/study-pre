package org.example.annotation;

/**
 * @Author yanzheng
 * @Date 2023/1/29 11:17
 */
public class FunctionalInterfaceTest {

    public static void main(String[] args) {
        FunInterface funInterface = new FunInterface() {
            @Override
            public void test() {
                System.out.println("你好");
            }
        };
        // lambda 写法
        FunInterface funInterface1 = () -> System.out.println("你好");

        funInterface.test();
        funInterface1.test();


        // 例如这种 lambda 写法 重写函数式接口的抽象方法
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    /**
     * 函数式接口定义: 函数式接口首先必须是一个接口，
     * 接口里面只能有一个抽象方法（允许有默认方法、静态方法等）；
     * 这种类型的接口也称为SAM接口，即Single Abstract Method Interface。:
     * 例如 :Thread 中的 Runnable 接口 如上
     * 使用 @FunctionalInterface 作用是 进行上述规定的约束,标识此接口未函数式接口,不符合则抛出编译时错误
     */
    @FunctionalInterface
    public interface FunInterface {
        static void print() {
            System.out.println("C语言中文网");
        }

        default void show() {
            System.out.println("我正在学习C语言中文网Java教程");
        }

        void test(); // 只定义一个抽象方法
    }
}
