package org.example.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @Author yanzheng
 * @Date 2022/12/22 21:46
 */
public class ServerInboundHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //客户端写回数据 写的数据需要时  byteBuf 类型的

        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("hellow,client".getBytes(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(buffer);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
    * 有数据的时候回调
     * @param msg Object 在netty中都是ByteBuf类型的
    * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf= (ByteBuf) msg;
        System.out.println("server 数据接收到"+buf.toString(StandardCharsets.UTF_8));
        super.channelRead(ctx, msg);
    }

    /**
    * 缓存区的所有数据都读完
    * */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //客户端写回数据 写的数据需要时  byteBuf 类型的

        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("hellow,client".getBytes(StandardCharsets.UTF_8));
         ctx.channel().writeAndFlush(buffer);
        super.channelReadComplete(ctx);
    }
}
