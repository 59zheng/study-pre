package org.example.rpc.client.clustr.lb;


import cn.hutool.core.util.RandomUtil;
import org.example.rpc.annotation.ZrpcLoadBalance;
import org.example.rpc.client.clustr.LoadBalanceStrategy;
import org.example.rpc.provider.ServiceProvider;

import java.util.List;

@ZrpcLoadBalance(strategy = "random")
public class RandomLoadBalanceStrategy implements LoadBalanceStrategy {


    @Override
    public ServiceProvider select(List<ServiceProvider> serviceProviders) {
        /**
         * [0,len)
         */
        int len = serviceProviders.size();
        int index = RandomUtil.randomInt(0, len);
        return serviceProviders.get(index);
    }
}
