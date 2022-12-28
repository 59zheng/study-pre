package org.example.rpc.client.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author yanzheng
 * @Date 2022/12/28 15:59
 */
@Configuration
@Slf4j
public class RpcBootstrap {



    @Autowired
    private RpcClientRunner rpcClientRunner;

    /**
     * 只能修饰一个非静态的方法,且无返回值,无参数,不可以抛出异常,   在select 容器 移动之后执行一次
     * 执行顺讯  @Component -> @Autowired -> @PostConstruct
     * */
    @PostConstruct
    public void initRpcStart() {
        log.info("进来了没啊");

        rpcClientRunner.start();


    }
}
