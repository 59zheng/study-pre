package org.example.rpc.client.clustr;

import org.example.rpc.annotation.ZrpcLoadBalance;
import org.example.rpc.client.config.RpcClientConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DefaultStartegyProvider implements StrategyProvider, ApplicationContextAware {

    @Autowired
    private RpcClientConfiguration configuration;

    LoadBalanceStrategy loadBalanceStrategy;

    @Override
    public LoadBalanceStrategy getStrategy() {
        return loadBalanceStrategy;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(ZrpcLoadBalance.class);
        if (!beansWithAnnotation.isEmpty()) {
            for (Object bean : beansWithAnnotation.values()) {
                ZrpcLoadBalance hrpcLoadBalance = bean.getClass().getAnnotation(ZrpcLoadBalance.class);
                String strategy = hrpcLoadBalance.strategy();
                if (strategy.equalsIgnoreCase(configuration.getRpcClientClusterStrategy())) {
                    loadBalanceStrategy = (LoadBalanceStrategy) bean;
                    break;
                }
            }
        }
    }
}
