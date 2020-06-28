package com.matrix.proxy.webscoket;

import com.google.common.base.Optional;
import io.netty.channel.Channel;

import java.util.Map;

/**
 * @ClassName WebConnection
 * @Author QIANGLU
 * @Date 2020/4/6 11:09 上午
 * @Version 1.0
 */
public interface WebConnectionStore {

    WebConnection register(Channel channel);

    Optional<WebConnection> getConnection(Channel channel);

    Map<Channel, WebConnection> getAllConnection();

}
