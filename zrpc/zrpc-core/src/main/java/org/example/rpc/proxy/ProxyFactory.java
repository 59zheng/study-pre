package org.example.rpc.proxy;

/**
 * @Author yanzheng
 * @Date 2022/12/30 15:51
 */
public interface ProxyFactory {


    /**
    * 代理实例
    * */
    public  <T> T newProxyInstance(Class<T> cls) ;
}
