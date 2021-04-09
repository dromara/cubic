package com.cubic.proxy.common.server;

import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName Processor
 * @Author QIANGLU
 * @Date 2020/3/19 11:16 上午
 * @Version 1.0
 */
public interface MessageProcessor {

    Integer code();

    void process(ChannelHandlerContext ctx, String datagram);
}
