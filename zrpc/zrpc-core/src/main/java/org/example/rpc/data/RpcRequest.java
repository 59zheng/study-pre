package org.example.rpc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yanzheng
 * @Date 2022/12/28 14:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest {

    private String requestId;

    private String className;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;
}
