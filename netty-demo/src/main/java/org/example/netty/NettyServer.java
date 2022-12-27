package org.example.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.example.netty.server.ServerInboundHandle;
import org.example.netty.server.ServerInboundHandle1;

/**
 * @Author yanzheng
 * @Date 2022/12/22 21:30
 */
public class NettyServer {


    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(2004);
    }

    public void start(int port) {
//        准备 eventLoop
        NioEventLoopGroup boos = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerInboundHandle1 serverInboundHandle1 = new ServerInboundHandle1();
        try {
        //核心引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boos, worker)
                .channel(NioServerSocketChannel.class)  //boos 的channel 类型

                //BOOS 的handle
                .childHandler(new ChannelInitializer<SocketChannel>() {   //worker 的handle
                    /**
                     * 客户端channel 初始化时回调
                     * */
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //添加具体逻辑 handle
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast(new ServerInboundHandle());
                        //代码编写习惯吧，channel 不断 pipeline 一直存在 ServerInboundHandle 实例不会销毁，超大连接数可以节省内存
                        //需要考虑内部的线程安全问题
                        pipeline.addFirst(serverInboundHandle1);
                    }

                });
        //绑定端口启动 默认是异步执行的，不会阻塞主线程

            ChannelFuture sync = serverBootstrap.bind(port).sync();
            //监听端口关闭 sync 等待 端口关闭 保证程序长活
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            // 端口关闭之后 释放资源
            worker.shutdownGracefully();
            boos.shutdownGracefully();
        }


    }
}
