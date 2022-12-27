package org.example.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @Author yanzheng
 * @Date 2022/12/22 21:46
 */
@ChannelHandler.Sharable
public class ServerInboundHandle1 extends SimpleChannelInboundHandler<ByteBuf> {




    /**
    * 有数据的时候回调
     * @param msg Object 在netty中都是ByteBuf类型的
    * */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("server1 数据接收到"+msg.toString(StandardCharsets.UTF_8));

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
