package org.example.shop.service;

import org.example.rpc.annotation.ZrpcService;
import org.example.rpc.server.config.RpcServerConfiguration;
import org.example.shop.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description
 * @author: ts
 * @create:2021-05-10 10:57
 */

@ZrpcService(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RpcServerConfiguration serverConfiguration;

    @Override
    public String getOrder(String userId, String orderNo) {
        return serverConfiguration.getServerPort() + "---" + serverConfiguration.getRpcPort() + "---Congratulations, The RPC call succeeded,orderNo is " + orderNo + ",userId is " + userId;
    }
}
