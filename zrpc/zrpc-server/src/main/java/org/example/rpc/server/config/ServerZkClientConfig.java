package org.example.rpc.server.config;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yanzheng
 * @Date 2022/12/28 17:33
 */
@Configuration
@Slf4j
public class ServerZkClientConfig {

    /**
     * RPC服务端配置
     */
    @Autowired
    private RpcServerConfiguration rpcServerConfiguration;

    /**
     * 申明 ZK客户端
     * @return
     */
    @Bean
    public ZkClient zkClient() {
        log.info("zk ip,port{},timout{}",rpcServerConfiguration.getZkAddr(),rpcServerConfiguration.getConnectTimeout());
        return new ZkClient(rpcServerConfiguration.getZkAddr(), rpcServerConfiguration.getConnectTimeout());
    }
}
