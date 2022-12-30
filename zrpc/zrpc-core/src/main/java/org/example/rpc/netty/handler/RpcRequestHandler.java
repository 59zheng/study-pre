package org.example.rpc.netty.handler;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.data.RpcRequest;
import org.example.rpc.data.RpcResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author yanzheng
 * @Date 2022/12/28 15:12
 */

@Slf4j
@ChannelHandler.Sharable
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {


    /**
     * 基于反射拿到 请求的调用目标类,方法,参数调用,返回结果
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        RpcResponse rpcResponse = new RpcResponse();
        log.info("server receive  msg {}", JSONUtil.toJsonStr(request));

        try {
             Object bean = SpringUtil.getBean(Class.forName(request.getClassName()));

            //reflect invoke 反射代理
            Method method = bean.getClass().getMethod(request.getMethodName(), request.getParameterTypes());

            Object invoke = method.invoke(bean, request.getParameters());

            rpcResponse.setRequestId(request.getRequestId());

            rpcResponse.setResult(invoke);

        } catch (Exception e) {
            log.error("rpc server invoke error msg{}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            log.info("server reponse  msg {}", JSONUtil.toJsonStr(rpcResponse));

            ctx.channel().writeAndFlush(rpcResponse);
        }


    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
