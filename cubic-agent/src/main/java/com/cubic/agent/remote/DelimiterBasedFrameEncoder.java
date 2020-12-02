package com.cubic.agent.remote;


import com.cubic.agent.utils.GzipUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName DelimiterBasedFrameEncoder
 * @Author QIANGLU
 * @Date 2020/3/24 10:23 上午
 * @Version 1.0
 */
public class DelimiterBasedFrameEncoder extends MessageToByteEncoder<String> {

    private final String delimiter;

    public DelimiterBasedFrameEncoder(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        ctx.writeAndFlush(Unpooled.wrappedBuffer((GzipUtils.compress(msg) + delimiter).getBytes()));
    }
}
