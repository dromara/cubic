package com.matrix.proxy.encoder;

import com.matrix.proxy.util.GzipUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @ClassName DelimiterBasedFrameEncoder
 * @Author QIANGLU
 * @Date 2020/3/24 10:23 上午
 * @Version 1.0
 */
public class GzipDecoder extends MessageToMessageDecoder<String> {


    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        out.add(GzipUtils.decompress(msg));
    }
}
