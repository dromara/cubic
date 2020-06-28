package com.matrix.proxy.webscoket;

import com.matrix.proxy.common.Connection;
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
