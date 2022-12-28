package org.example.rpc.server.registry.zk;

import org.I0Itec.zkclient.ZkClient;
import org.example.rpc.server.config.RpcServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author yanzheng
 * @Date 2022/12/28 00:29
 */
@Component
public class ServerZKit {

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private RpcServerConfiguration rpcServerConfiguration;

    /**
     * 根节点创建
     */
    public void createRootNode() {
        boolean exists = zkClient.exists(rpcServerConfiguration.getZkRoot());
        if (!exists) {
            zkClient.createPersistent(rpcServerConfiguration.getZkRoot());
        }
    }

    /***
     * 创建其他节点
     * @param path
     */
    public void createPersistentNode(String path) {
        String pathName = rpcServerConfiguration.getZkRoot() + "/" + path;
        boolean exists = zkClient.exists(pathName);
        if (!exists) {
            zkClient.createPersistent(pathName);
        }
    }

    /***
     * 创建节点
     * @param path
     */
    public void createNode(String path) {
        String pathName = rpcServerConfiguration.getZkRoot() + "/" + path;
        boolean exists = zkClient.exists(pathName);
        if (!exists) {
            zkClient.createEphemeral(pathName);
        }
    }


}
