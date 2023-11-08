package org.example.concurrent;

/**
 * @Author yanzheng
 * @Date 2023/7/25 10:46
 */
public class FinalReferenceEscapeDemo {
    private final int a;
    private FinalReferenceEscapeDemo referenceEscapeDemo;

    public FinalReferenceEscapeDemo() {
        a = 1; //1
        referenceEscapeDemo = this; //2
    }

    public void write() {
        new FinalReferenceEscapeDemo(); //3
    }

    public void reader() {
        if (referenceEscapeDemo != null) {  //3
            int temp = referenceEscapeDemo.a; //4
        }
    }

    public static void main(String[] args) {
        final byte b1=1;
        final byte b2=3;
        byte b3=b1+b2;
    }
}
