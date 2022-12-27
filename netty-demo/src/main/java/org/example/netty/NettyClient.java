package org.example.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.netty.client.ClientInboundHandle;

import java.nio.charset.StandardCharsets;

/**
 * @Author yanzheng
 * @Date 2022/12/22 21:58
 */
public class NettyClient {

    public static void main(String[] args) {

        connect("127.0.0.1", 2004);
    }

    public static void connect(String host, int port) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {


                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //建议连接之后处理
                            ch.pipeline().addLast(new ClientInboundHandle());
                        }
                    });

            ChannelFuture sync = bootstrap.connect(host, port).sync();
            Channel channel = sync.channel();
            ByteBuf buffer = channel.alloc().buffer();
            buffer.writeBytes("hello nettyServer".getBytes(StandardCharsets.UTF_8));
            channel.writeAndFlush(buffer);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            eventExecutors.shutdownGracefully();
        }

    }
}
