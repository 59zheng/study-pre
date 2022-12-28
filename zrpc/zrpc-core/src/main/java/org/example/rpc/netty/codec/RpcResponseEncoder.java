package org.example.rpc.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.data.RpcResponse;
import org.example.rpc.util.ProtostuffUtil;

import java.util.List;

/**
 * @Author yanzheng
 * @Date 2022/12/28 15:03
 */
@Slf4j
public class RpcResponseEncoder extends MessageToMessageDecoder<RpcResponse> {


    /**
     * 二次 编码过程
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, RpcResponse msg, List<Object> out) throws Exception {

        try {
            byte[] bytes = ProtostuffUtil.doSerialize(msg);
            ByteBuf buf = ctx.alloc().buffer(bytes.length).writeBytes(bytes);
            out.add(buf);
        } catch (Exception e) {
            log.info("RpcResponseEncoder encode error msg{}", e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
