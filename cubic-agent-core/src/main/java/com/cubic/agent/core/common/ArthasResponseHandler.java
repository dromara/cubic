package com.cubic.agent.core.common;


import com.cubic.agent.core.remote.ResponseHandler;
import com.cubic.agent.core.remote.ResponseWriter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName ArthasResponseHandler
 * @Author QIANGLU
 * @Date 2020/4/21 2:47 下午
 * @Version 1.0
 */
public class ArthasResponseHandler implements ResponseHandler {

    private final ChannelHandlerContext ctx;

    private final String id;

    public ArthasResponseHandler(ChannelHandlerContext ctx, String id) {
        this.ctx = ctx;
        this.id = id;
    }

    private final ResponseWriter writer = ResponseWriter.getInstance();

    @Override
    public void handle(String line) {
        writer.arthasWrite(ctx, line, id);
    }

    @Override
    public void handleEOF() {

    }

    @Override
    public void handleError(Throwable t) {
        writer.arthasWriteError(ctx, t.getMessage(), id);
    }

    @Override
    public void write(Integer code, String content) {
        writer.write(ctx, code, content, id);
    }
}
