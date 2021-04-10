
package com.cubic.proxy.common.server;

import com.google.common.util.concurrent.ListenableFuture;

public interface Connection {


    String write(Object message, SyncFuture syncFuture);

    public void write(Object message);

    ListenableFuture<Void> closeFuture();

    boolean isActive();

    void close();
}
