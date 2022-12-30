package org.example.rpc.client.clustr;

import org.example.rpc.provider.ServiceProvider;

import java.util.List;

public interface LoadBalanceStrategy {

    /**
     * 根据对应的策略进行负载均衡
     * @param serviceProviders
     * @return
     */
    ServiceProvider select(List<ServiceProvider> serviceProviders);

}
