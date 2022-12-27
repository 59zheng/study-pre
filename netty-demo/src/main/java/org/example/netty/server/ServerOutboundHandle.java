package org.example.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @Author yanzheng
 * @Date 2022/12/24 09:53
 */
public class ServerOutboundHandle extends ChannelOutboundHandlerAdapter {


    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.disconnect(ctx, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("ServerOutboundHandle write"+msg.toString());
        super.write(ctx, msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}
