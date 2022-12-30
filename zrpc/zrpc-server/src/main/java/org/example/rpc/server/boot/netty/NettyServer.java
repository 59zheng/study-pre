package org.example.rpc.server.boot.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.server.boot.RpcServer;
import org.example.rpc.netty.codec.FrameDecoder;
import org.example.rpc.netty.codec.FrameEncoder;
import org.example.rpc.netty.codec.RpcRequestDecoder;
import org.example.rpc.netty.codec.RpcResponseEncoder;
import org.example.rpc.netty.handler.RpcRequestHandler;
import org.example.rpc.server.config.RpcServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;

/**
 * @Author yanzheng
 * @Date 2022/12/28 00:00
 */
@Component
@Slf4j
public class NettyServer implements RpcServer {


    @Autowired
    private RpcServerConfiguration configuration;

    @Override
    public void start() {

        NioEventLoopGroup boss = new NioEventLoopGroup(1, new DefaultThreadFactory("boos"));
        NioEventLoopGroup worker = new NioEventLoopGroup(0, new DefaultThreadFactory("worker"));
        NioEventLoopGroup business = new NioEventLoopGroup(NettyRuntime.availableProcessors() * 2, new DefaultThreadFactory("business"));

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        RpcRequestHandler rpcRequestHandler = new RpcRequestHandler();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                        pipeline.addLast("frameEncoder", new FrameEncoder());
                        pipeline.addLast("rpcResponseEncoder", new RpcResponseEncoder());


                        pipeline.addLast("frameDecoder", new FrameDecoder());
                        pipeline.addLast("rpcRequestDecoder", new RpcRequestDecoder());

                        pipeline.addLast(business, "rpcRequestHandler", rpcRequestHandler);


                    }
                });
        try {
            ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(configuration.getRpcPort())).sync();
            log.info("rpc server started on port ={}", configuration.getRpcPort());
            // 阻塞等待监听关闭
            future.channel().closeFuture().addListener((f) -> {
                boss.shutdownGracefully();
                worker.shutdownGracefully();
                business.shutdownGracefully();
            });

        } catch (InterruptedException e) {
            e.printStackTrace();

            boss.shutdownGracefully();
            worker.shutdownGracefully();
            business.shutdownGracefully();
        }

    }
}
