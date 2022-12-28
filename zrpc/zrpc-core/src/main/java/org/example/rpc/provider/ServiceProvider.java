package org.example.rpc.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author yanzheng
 * @Date 2022/12/28 16:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProvider implements Serializable {
    private String serviceName;
    private String serverIp;
    private int rpcPort;
    private int networkPort;
    private long timeout;
    // the weight of service provider
    private int weight;
}
