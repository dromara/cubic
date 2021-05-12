package com.cubic.agent.remote;

import com.cubic.serialization.agent.v1.CommonMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName ResponseWriter
 * @Author QIANGLU
 * @Date 2020/4/22 10:35 上午
 * @Version 1.0
 */
public class ResponseWriter {

    private final static ResponseWriter WRITER = new ResponseWriter();

    public static ResponseWriter getInstance() {
        return WRITER;
    }

    public void arthasWrite(ChannelHandlerContext ctx, String content, String id) {
        write(ctx, CommandCode.ARTHAS.getCode(), content, id);
    }

    public void arthasWriteError(ChannelHandlerContext ctx, String content, String id) {
        write(ctx, CommandCode.ARTHAS.getCode(), content, id);
    }

    public void write(ChannelHandlerContext ctx, Integer code, String content, String id) {
        CommonMessage message = CommonMessage.newBuilder()
                .setCode(code)
                .setBody(content)
                .setId(id)
                .build();
//        Message message = new Message(code, content, id);
//        Gson gson = new Gson();
        ctx.writeAndFlush(message);
    }
}
