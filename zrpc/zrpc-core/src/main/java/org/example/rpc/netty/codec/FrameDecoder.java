package org.example.rpc.netty.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Author yanzheng
 * @Date 2022/12/28 14:43
 */

public class FrameDecoder extends LengthFieldBasedFrameDecoder {

    /**
     * 指定 hard 头长度 + 可变长body 的一次解码
     */
    public FrameDecoder() {
        //  最大 frame 长度  head 偏移量字节   head 长度  head与body 间隙 总长度
        super(Integer.MAX_VALUE, 0, 4, 0, 4);
    }
}
