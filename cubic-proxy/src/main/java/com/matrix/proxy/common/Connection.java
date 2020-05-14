
package com.matrix.proxy.common;

import com.google.common.util.concurrent.ListenableFuture;
import com.matrix.proxy.server.SyncFuture;

public interface Connection {


    String write(Object message, SyncFuture syncFuture);

    public void write(Object message);

    ListenableFuture<Void> closeFuture();

    boolean isActive();

    void close();
}
