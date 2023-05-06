
package com.cubic.agent.core.boot;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultNamedThreadFactory implements ThreadFactory {
    private static final AtomicInteger BOOT_SERVICE_SEQ = new AtomicInteger(0);
    private final AtomicInteger threadSeq = new AtomicInteger(0);
    private final String namePrefix;

    public DefaultNamedThreadFactory(String name) {
        namePrefix = "Cubic-" + BOOT_SERVICE_SEQ.incrementAndGet() + "-" + name + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, namePrefix + threadSeq.getAndIncrement());
        t.setDaemon(true);
        return t;
    }
}
