package org.example.rpc.client.clustr.lb;


import cn.hutool.core.util.RandomUtil;
import org.example.rpc.annotation.ZrpcLoadBalance;
import org.example.rpc.client.clustr.LoadBalanceStrategy;
import org.example.rpc.provider.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

@ZrpcLoadBalance(strategy = "weight_random")
public class WeightRandomLoadBalanceStrategy implements LoadBalanceStrategy {
    @Override
    public ServiceProvider select(List<ServiceProvider> serviceProviders) {
        /**
         * 按照权重创建一个新的待选集合
         * 在新的集合中随机选择
         */
        List<ServiceProvider> newList = new ArrayList<>();
        for (ServiceProvider serviceProvider : serviceProviders) {
            //获取该节点的权重
            int weight = serviceProvider.getWeight();
            //根据权重向新集合中添加节点
            for (int i=0;i<weight;i++) {
                newList.add(serviceProvider);
            }
        }
        //在新集合中进行随机选取
        int index = RandomUtil.randomInt(0, newList.size());

        return newList.get(index);
    }
}
