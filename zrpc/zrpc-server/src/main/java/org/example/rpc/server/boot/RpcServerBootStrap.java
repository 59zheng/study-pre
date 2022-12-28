package org.example.rpc.server.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author yanzheng
 * @Date 2022/12/27 22:35
 */

/**
 *
 */
@Configuration
@Slf4j
public class RpcServerBootStrap {


    @Autowired
    private RpcServerRunner rpcServerRunner;

    /**
     * 只能修饰一个非静态的方法,且无返回值,无参数,不可以抛出异常,   在select 容器 移动之后执行一次
     * 执行顺讯  @Component -> @Autowired -> @PostConstruct
     */
    @PostConstruct
    public void initRpcStart() {
        log.info("都没过来吗?");
        rpcServerRunner.start();


    }
}
