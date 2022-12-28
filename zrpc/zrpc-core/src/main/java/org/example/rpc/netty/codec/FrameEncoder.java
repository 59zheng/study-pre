package org.example.rpc.netty.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @Author yanzheng
 * @Date 2022/12/28 14:47
 */
public class FrameEncoder extends LengthFieldPrepender {
    /**
    * head 标识 可变body 长度 编码类
    * */
    public FrameEncoder() {
        super(4);
    }
}
