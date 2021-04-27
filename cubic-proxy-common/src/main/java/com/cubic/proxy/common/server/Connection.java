
package com.cubic.proxy.common.server;

import com.cubic.serialization.agent.v1.CommonMessage;
import com.google.common.util.concurrent.ListenableFuture;

public interface Connection {


    String write(CommonMessage message, SyncFuture syncFuture);

    void write(CommonMessage message);

    ListenableFuture<Void> closeFuture();

    boolean isActive();

    void close();
}
