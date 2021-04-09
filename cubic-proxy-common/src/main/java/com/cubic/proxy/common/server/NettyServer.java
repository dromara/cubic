package com.cubic.proxy.common.server;

/**
 * @ClassName NettyServer
 * @Author QIANGLU
 * @Date 2020/3/19 2:29 下午
 * @Version 1.0
 */
public interface NettyServer {
    void start();

    boolean isActive();

    void stop();
}
