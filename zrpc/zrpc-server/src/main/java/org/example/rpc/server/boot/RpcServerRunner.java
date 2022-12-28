package org.example.rpc.server.boot;

import org.example.rpc.server.registry.RpcRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

/**
 * @Author yanzheng
 * @Date 2022/12/27 22:36
 */
@Component
public class RpcServerRunner {

    @Autowired
    private RpcRegistry registry;

    @Autowired
    private RpcServer server;


    /**
     * 流程
     * 1, 服务注册:
     * 1-1,扫描ZrpcService 注解,拿到需要暴露的 rpc 接口服务
     * 2-2,向注册中心写入相关信息[信息结构:]
     * 2, 基于 netty 编写一个服务端程序,启动,提供对应编解码器,以及处理对应请求的 handler
     */
    public void start() {

        registry.serviceRegistry();

        server.start();
    }
}
