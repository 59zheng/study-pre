package com.itheima.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
/**
* extends AbstractNameValueGatewayFilterFactory  可以获取到当前的断言.即拦截的目录以及 对应的地址
 * 配置文件中的 routes: id / uri
* */
@Slf4j
@Component
public class TokenGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

                    String name = config.getName();
                    String value = config.getValue();
                    log.info("PayMethodGatewayFilterFactory 加载到的配置信息为:{}---{}",name,value);
                    //将paymethod添加到请求头中
                    exchange.getRequest().mutate().header(name,value);
                    return chain.filter(exchange);
            }
        };
    }
}
