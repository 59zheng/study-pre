package org.example.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @Author yanzheng
 * @Date 2022/12/22 22:08
 */
public class ClientInboundHandle extends ChannelInboundHandlerAdapter {

    /**
    * 通道建立完成之后调用一次
    * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf= (ByteBuf) msg;
        System.out.println("client 数据接收到"+buf.toString(StandardCharsets.UTF_8));


        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //客户端写回数据 写的数据需要时  byteBuf 类型的
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("hellow,server".getBytes(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(buffer);
        super.channelReadComplete(ctx);
    }
}
