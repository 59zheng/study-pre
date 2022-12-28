package org.example.rpc.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.data.RpcRequest;
import org.example.rpc.util.ProtostuffUtil;

import java.util.List;

/**
 * @Author yanzheng
 * @Date 2022/12/28 14:49
 */
@Slf4j
public class RpcRequestDecoder extends MessageToMessageDecoder<ByteBuf> {

    /**
     * 二次解码  bytebuf 转 request请求封装
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {

        try {
            int length = msg.readableBytes();
            byte[] bytes = new byte[length];
            msg.readBytes(bytes);
            //protostuff 序列化 解码, 性能较好
            RpcRequest rpcRequest = ProtostuffUtil.deserialize(bytes, RpcRequest.class);

            out.add(rpcRequest);
        } catch (Exception e) {
            log.error("RpcRequestDecoder decode error,msg={}", e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
