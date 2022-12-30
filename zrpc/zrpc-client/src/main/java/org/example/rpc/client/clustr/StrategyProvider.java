package org.example.rpc.client.clustr;

public interface StrategyProvider {

    LoadBalanceStrategy getStrategy();
}
