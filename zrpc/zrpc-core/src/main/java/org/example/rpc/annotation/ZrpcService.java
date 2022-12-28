package org.example.rpc.annotation;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ZrpcService {

    /**
     * 指定别名,可赋值给继承的注解 等同于指定了Component 的value
     */
    @AliasFor(annotation = Component.class)
    String value() default "";

    /**
     * 接口类的类地址 默认自身的class 地址
     */
    Class<?> interfaceClass() default void.class;


}
