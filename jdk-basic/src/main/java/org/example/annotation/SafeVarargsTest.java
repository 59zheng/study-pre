package org.example.annotation;

/**
 * @Author yanzheng
 * @Date 2023/1/29 11:05
 */
public class SafeVarargsTest {
    public static void main(String[] args) {

// 传递可变参数，参数是泛型集合
        display(1, 2, 3, 4);

        // 传递可变参数，参数是非泛型集合  编译器会抛预警
        display("aaa", 2, 3, 4);
    }

    /**
     * T... 可变长度 的泛型T 就可变长数组
     * @SafeVarargs 注解可以抑制 传递参数类型不同导致的编译器报错.
     * 当然也可以使用 @SuppressWarnings("unchecked") 注解，但是两者相比较来说 @SafeVarargs 注解更适合。
     */
    @SafeVarargs
    public static <T> void display(T... array) {
        for (T arg : array) {
            System.out.println(arg.getClass().getName() + ":" + arg);
        }
    }

}
