package org.example.rpc.client.spring;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.annotation.ZrpcRemote;
import org.example.rpc.annotation.ZrpcService;
import org.example.rpc.proxy.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @Author yanzheng
 * @Date 2022/12/30 15:49
 */
@Component
@Slf4j
public class RpcAnnotationProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ProxyFactory proxyFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        proxyFactory = applicationContext.getBean(ProxyFactory.class);
    }

    /**
     * 扫描所有bean   拥有 @ZrpcRemote 注解引用  对指定方法进行增强
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        try {
            for (Field field : declaredFields) {
                ZrpcRemote annotation = field.getAnnotation(ZrpcRemote.class);

                if (null != annotation) {
                    // private 私有的无法访问,设置为可访问
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }

                    Class<?> interfaceClass = field.getType();

                    Object proxyInstance = proxyFactory.newProxyInstance(interfaceClass);

                    field.set(bean, proxyInstance);


                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
