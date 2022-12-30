package org.example.rpc.netty.handler;

import cn.hutool.json.JSONUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.data.RpcResponse;
import org.example.rpc.request.RequestPromise;
import org.example.rpc.request.RpcRequestHolder;

/**
 * @Author yanzheng
 * @Date 2022/12/30 16:58
 */
@ChannelHandler.Sharable
@Slf4j
public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        log.info("client receive msg{}", JSONUtil.toJsonStr(msg));
        //成功监听回调
        RequestPromise requestPromise = RpcRequestHolder.getRequestPromise(msg.getRequestId());
        if (requestPromise != null) {
            requestPromise.setSuccess(msg);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
