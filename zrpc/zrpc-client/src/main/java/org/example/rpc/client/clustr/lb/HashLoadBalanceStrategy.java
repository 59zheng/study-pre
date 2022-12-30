package org.example.rpc.client.clustr.lb;

import cn.hutool.core.net.Ipv4Util;
import org.example.rpc.annotation.ZrpcLoadBalance;
import org.example.rpc.client.clustr.LoadBalanceStrategy;
import org.example.rpc.provider.ServiceProvider;
import org.example.rpc.util.IpUtil;

import java.util.List;

@ZrpcLoadBalance(strategy = "hash")
public class HashLoadBalanceStrategy implements LoadBalanceStrategy {

    @Override
    public ServiceProvider select(List<ServiceProvider> serviceProviders) {
        /**
         * 1、获取客户端ip
         * 2、获取ip hash
         * 3、index = hash % serviceProviders.size()
         * 4、get(index)
         */
        String ip = IpUtil.getRealIp();
        int hashCode = ip.hashCode();
        int index = Math.abs(hashCode % serviceProviders.size());
        return serviceProviders.get(index);
    }
}
