package org.example.rpc.client.proxy;

import org.example.rpc.proxy.ProxyFactory;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

/**
 * @Author yanzheng
 * @Date 2022/12/30 16:15
 */
@Component
public class RequestProxyFactory implements ProxyFactory {


    /**
     * 创建新的代理实例-CGLib动态代理
     */
    @Override
    public <T> T newProxyInstance(Class<T> cls) {
        // cglib 的增强器
        Enhancer enhancer = new Enhancer();
        // 增强的目标 class 如果是 interface 使用 setInterface
        enhancer.setSuperclass(cls);

        enhancer.setCallback( new CglibProxyCallBackHandler());

        return (T) enhancer.create();
    }
}
