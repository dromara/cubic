package com.cubic.proxy.common.webserver;

import com.cubic.proxy.common.server.Connection;
import io.netty.channel.Channel;

/**
 * websocket connection
 * @ClassName WebConnection
 * @Author QIANGLU
 * @Date 2020/4/6 11:09 上午
 * @Version 1.0
 */
public interface WebConnection extends Connection {

    Channel getChannel();
}
