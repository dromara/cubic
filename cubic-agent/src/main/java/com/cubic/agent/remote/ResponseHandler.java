package com.cubic.agent.remote;

import com.cubic.agent.module.Message;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName ResponseHandler
 * @Author QIANGLU
 * @Date 2020/4/21 2:45 下午
 * @Version 1.0
 */
public interface ResponseHandler {

    void handle(String line);

    void handleEOF();

    void handleError(Throwable t);


    void write(Integer code, String content);
}
