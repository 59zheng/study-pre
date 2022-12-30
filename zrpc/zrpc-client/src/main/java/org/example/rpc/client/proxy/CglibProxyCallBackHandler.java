package org.example.rpc.client.proxy;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.example.rpc.client.request.RpcRequestManager;
import org.example.rpc.data.RpcRequest;
import org.example.rpc.data.RpcResponse;
import org.example.rpc.util.RequestIdUtil;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @Author yanzheng
 * @Date 2022/12/30 16:18
 */
@Slf4j
public class CglibProxyCallBackHandler implements MethodInterceptor {


    /**
     * 目标方法调用
     */
    @Override
    public Object intercept(Object o, Method method, Object[] parameters, MethodProxy methodProxy) throws Throwable {
        log.info("CgLibProxyCallBack-----------interceptor");
        // 封装 Rpc请求
        //放过 toString equest 等 重写Object 类的方法
        if (ReflectionUtils.isObjectMethod(method)) {
            return method.invoke(method.getDeclaringClass().newInstance(), parameters);
        }
        RpcRequest request = RpcRequest.builder()
                .requestId(RequestIdUtil.requestId())
                .className(method.getDeclaringClass().getName())
                .parameters(parameters)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .build();
        //请求构建
        RpcRequestManager manager = SpringUtil.getBean(RpcRequestManager.class);
        RpcResponse response = manager.sendRequest(request);
        //4、返回结果数据
        return response.getResult();
    }
}
