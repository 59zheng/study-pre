package com.itheima.predicate;

import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class TokenRoutePredicateFactory extends AbstractRoutePredicateFactory<TokenRoutePredicateFactory.Config> {

    /**
     * Name key.
     */
    public static final String NAME_KEY = "tokenName";

    /**
     * Regexp key.
     */
    public static final String REGEXP_KEY = "tokenRegxp";

    public TokenRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(NAME_KEY, REGEXP_KEY);
    }


    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                List<String> list = serverWebExchange.getRequest().getHeaders().get(config.getTokenName());
                if (list==null || list.isEmpty()) {
                    return false;
                }
                // 基于token值 进行校验
                // .......
                return true;
            }
        };
    }

    @Data
    public static class Config{

        private  String tokenName;

        private  String tokenRegxp;

    }
}
