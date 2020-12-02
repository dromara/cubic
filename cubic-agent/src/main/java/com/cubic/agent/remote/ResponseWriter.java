package com.cubic.agent.remote;

import com.google.gson.Gson;
import com.cubic.agent.module.Message;
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
        Message message = new Message(code, content, id);
        Gson gson = new Gson();
        ctx.writeAndFlush(gson.toJson(message));
    }
}
