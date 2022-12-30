package org.example.rpc.client.clustr.lb;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.annotation.ZrpcLoadBalance;
import org.example.rpc.client.clustr.LoadBalanceStrategy;
import org.example.rpc.provider.ServiceProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@ZrpcLoadBalance(strategy = "polling")
public class PollingLoadBalanceStrategy implements LoadBalanceStrategy {

    private int index;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public ServiceProvider select(List<ServiceProvider> serviceProviders) {
        try {
            lock.tryLock(10, TimeUnit.SECONDS);
            if(index>=serviceProviders.size()) {
                index = 0;
            }
            ServiceProvider serviceProvider = serviceProviders.get(index);
            index++;
            return serviceProvider;
        } catch (InterruptedException e) {
            log.error("轮询策略获取锁失败,msg={}",e.getMessage());
        } finally {
            lock.unlock();
        }
        return null;
    }
}
