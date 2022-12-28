package org.example.rpc.server.registry.zk;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.server.config.RpcServerConfiguration;
import org.example.rpc.server.registry.RpcRegistry;
import org.example.rpc.annotation.ZrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author yanzheng
 * @Date 2022/12/27 23:58
 */

@Component
@Slf4j
public class ZkRegistry implements RpcRegistry {

    @Autowired
    private ServerZKit serverZKit;

    @Autowired
    private RpcServerConfiguration configuration;


    @Override
    public void serviceRegistry() {
        //找到需要暴露的接口服务   获取 ApplicationContext 寻找指定注解注解的bean 通过实现 ApplicationContextAware 可以获取到加载好的 ApplicationContext
        Map<String, Object> beansWithAnnotation = SpringUtil.getApplicationContext().getBeansWithAnnotation(ZrpcService.class);
        log.info("暴露的服务{}", JSONUtil.toJsonStr(beansWithAnnotation));
        if (!beansWithAnnotation.isEmpty()) {
            // create zk root node
            serverZKit.createRootNode();
            String ip = SystemUtil.getHostInfo().getAddress();
            for (Object bean : beansWithAnnotation.values()) {

                //获取注解 ,拿到bean 的全名称
                ZrpcService zrpcService = bean.getClass().getAnnotation(ZrpcService.class);

                Class<?> interfaceClass = zrpcService.interfaceClass();

                String interfaceClassName = interfaceClass.getName();

                // create interface node 接口服务级别的目录
                serverZKit.createPersistentNode(interfaceClassName);

                //create provider node 服务提供者  beanName/ip:port
                String providerNode = interfaceClassName + "/" + ip + ":" + configuration.getRpcPort();

                serverZKit.createNode(providerNode);

                log.info("server 完成服务信息注册 service={},providder={},", interfaceClass, providerNode);
            }

        }
    }


}
