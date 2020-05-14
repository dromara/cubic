package com.matrix.proxy.common;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.matrix.proxy.server.SyncFuture;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public abstract class AbstractConnection implements Connection {
    private static final Logger logger = LoggerFactory.getLogger(AbstractConnection.class);

    private final String name;
    private final Channel channel;

    private final SettableFuture<Void> closeFuture = SettableFuture.create();

    public AbstractConnection(String name, Channel channel) {
        this.name = name;
        this.channel = channel;
    }

    public void init() {
        channel.closeFuture().addListener((f) -> closeFuture.set(null));
    }

    @Override
    public String write(Object message, SyncFuture syncFuture) {
        String result = "";
        try {
            if (channel.isWritable()) {
                channel.writeAndFlush(message).addListener(future -> {
                    if (future.isSuccess()) {
                        logger.info("write message succ");
                    } else {
                        logger.warn("{} connection write fail, {}, {}", name, channel, message);
                    }
                });
            } else {
                logger.warn("{} connection is not writable, {}, {}", name, channel, message);
            }
            if (syncFuture != null) {
                result = syncFuture.get(5, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void write(Object message) {
        write(message, null);
    }

    @Override
    public ListenableFuture<Void> closeFuture() {
        return closeFuture;
    }

    @Override
    public void close() {
        logger.info("close {} channel {}", name, channel);
        channel.close();
    }
}
