package org.example.rpc.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.data.RpcResponse;
import org.example.rpc.util.ProtostuffUtil;

import java.util.List;

/**
 * @Author yanzheng
 * @Date 2022/12/28 15:03
 */
@Slf4j
public class RpcResponseEncoder extends MessageToMessageEncoder<RpcResponse> {


    /**
     * 二次 编码过程
     */


    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse response, List<Object> out) throws Exception {
        try {
            byte[] bytes = ProtostuffUtil.serialize(response);
            ByteBuf buffer = ctx.alloc().buffer(bytes.length);
            buffer.writeBytes(bytes);
            out.add(buffer);
        } catch (Exception e) {
            log.error("RpcResponseEncoder encode error,msg={}",e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
