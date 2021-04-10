package com.cubic.proxy.websocket;

import com.cubic.proxy.common.server.AbstractConnection;
import com.cubic.proxy.common.webserver.WebConnection;
import io.netty.channel.Channel;

import java.util.Objects;

/**
 * @ClassName DefaultWebConnection
 * @Author QIANGLU
 * @Date 2020/4/6 11:15 上午
 * @Version 1.0
 */
public class DefaultWebConnection extends AbstractConnection implements WebConnection {

    private final Channel channel;

    public DefaultWebConnection(Channel channel) {
        super("web", channel);
        this.channel = channel;
    }

    @Override
    public boolean isActive() {
        return channel.isActive();
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultWebConnection that = (DefaultWebConnection) o;
        return Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel);
    }
}
