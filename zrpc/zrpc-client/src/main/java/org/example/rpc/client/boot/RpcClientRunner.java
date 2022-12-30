package org.example.rpc.client.boot;

import org.example.rpc.client.discovery.RpcServiceDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author yanzheng
 * @Date 2022/12/28 16:00
 */
@Component
public class RpcClientRunner {


    @Autowired
    private RpcServiceDiscovery serviceDiscovery;

    /**
     * 1,服务发现
     * 2,代理生成
     * 3,基于netty编写客户端
     */
    public void start() {

        serviceDiscovery.serviceDiscovery();

    }

}
