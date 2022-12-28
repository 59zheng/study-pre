package org.example.rpc.client.discovery.zk;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.cache.ServiceProviderCache;
import org.example.rpc.client.discovery.RpcServiceDiscovery;
import org.example.rpc.provider.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author yanzheng
 * @Date 2022/12/28 16:04
 */
@Component
@Slf4j
public class ZKServiceDiscovery implements RpcServiceDiscovery {

    @Autowired
    private ServiceProviderCache providerCache;

    @Autowired
    private ClientZKit zKit;

    /**
     * 服务发现
     */
    @Override
    public void serviceDiscovery() {
        // 获取根节点下 所有接口服务的 子节点
        List<String> serviceList = zKit.getServiceList();

        if (!CollectionUtils.isEmpty(serviceList)) {
            for (String interfaceName : serviceList) {

                List<ServiceProvider> serviceInfos = zKit.getServiceInfos(interfaceName);

                //put cache
                providerCache.put(interfaceName, serviceInfos);

                log.info("订阅的服务名为{},服务提供者{}", interfaceName, serviceInfos);

                // 订阅节点变动
                zKit.subscribeZKEvent(interfaceName);


            }
        }

    }
}
