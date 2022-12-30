package org.example.rpc.client.request;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.net.SocketServer;
import org.example.rpc.cache.ServiceProviderCache;
import org.example.rpc.client.clustr.LoadBalanceStrategy;
import org.example.rpc.client.clustr.StrategyProvider;
import org.example.rpc.client.config.RpcClientConfiguration;
import org.example.rpc.data.RpcRequest;
import org.example.rpc.data.RpcResponse;
import org.example.rpc.enums.StatusEnum;
import org.example.rpc.exception.RpcException;
import org.example.rpc.netty.codec.FrameDecoder;
import org.example.rpc.netty.codec.FrameEncoder;
import org.example.rpc.netty.codec.RpcRequestEncoder;
import org.example.rpc.netty.codec.RpcResponseDecoder;
import org.example.rpc.netty.handler.RpcResponseHandler;
import org.example.rpc.provider.ServiceProvider;
import org.example.rpc.request.ChannelMapping;
import org.example.rpc.request.RequestPromise;
import org.example.rpc.request.RpcRequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author yanzheng
 * @Date 2022/12/30 16:33
 */
@Component
@Slf4j
public class RpcRequestManager {


    @Autowired
    private RpcClientConfiguration configuration;

    @Autowired
    private StrategyProvider strategyProvider;

    @Autowired
    private ServiceProviderCache cache;

    /**
     * 1. ctx 判断通道复用 负载均衡
     * /1. netty client 启动
     * 2. 消息发送 异步等待返回  Promise get()
     * 3. RpcResponse 返回
     */
    public RpcResponse sendRequest(RpcRequest request) {
        log.info("client send msg {}", JSONUtil.toJsonStr(request));
        // 查找 provide 
        List<ServiceProvider> serviceProviders = cache.get(request.getClassName());
        if (CollectionUtils.isEmpty(serviceProviders)) {
            log.error("calss name {}provide cache null", request.getClassName());
            throw new RpcException(StatusEnum.NOT_FOUND_SERVICE_PROVINDER);
        }
        // load balance 负载均衡
        LoadBalanceStrategy strategy = strategyProvider.getStrategy();
        ServiceProvider select = strategy.select(serviceProviders);
        if (!ObjectUtil.isEmpty(select)) {
            return requestByNetty(request, select);
        } else {
            throw new RpcException(StatusEnum.NOT_FOUND_SERVICE_PROVINDER);
        }


    }

    private RpcResponse requestByNetty(RpcRequest request, ServiceProvider select) {
        Channel channel = null;
        try {


            // find channel
            if (!RpcRequestHolder.channelExist(select.getServerIp(), select.getRpcPort())) {
                // 没有 channel
                //client init
                NioEventLoopGroup eventExecutors = new NioEventLoopGroup(0, new DefaultThreadFactory("netty-client"));

                RpcResponseHandler rpcResponseHandler = new RpcResponseHandler();
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(eventExecutors)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                                pipeline.addLast("frameEncoder", new FrameEncoder());
                                pipeline.addLast("requestEncoder", new RpcRequestEncoder());

                                pipeline.addLast("frameDecoder", new FrameDecoder());
                                pipeline.addLast("rpcResponseDecoder", new RpcResponseDecoder());
                                pipeline.addLast("rpcResponseHandler", rpcResponseHandler);

                            }
                        });
                //同步起步才能拿到 channel
                ChannelFuture future = bootstrap.connect(select.getServerIp(), select.getRpcPort()).sync();

                channel = future.channel();

                // 缓存 连接 信息

                RpcRequestHolder.addChannelMapping(new ChannelMapping(select.getServerIp(), select.getRpcPort(), channel));

            } else {
                channel = RpcRequestHolder.getChannel(select.getServerIp(), select.getRpcPort());
            }
            // send msg
            RequestPromise requestPromise = new RequestPromise(channel.eventLoop());
            RpcRequestHolder.addRequestPromise(request.getRequestId(), requestPromise);
            channel.writeAndFlush(request);

            // wait response
            RpcResponse response = (RpcResponse) requestPromise.get(configuration.getConnectTimeout(), TimeUnit.SECONDS);
            RpcRequestHolder.removeRequestPromise(request.getRequestId());
            return response;


        } catch (Exception e) {
            e.printStackTrace();
            RpcRequestHolder.removeRequestPromise(request.getRequestId());
        }
        return new RpcResponse();
    }
}
