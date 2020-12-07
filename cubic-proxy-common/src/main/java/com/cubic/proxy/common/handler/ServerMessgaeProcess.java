package com.cubic.proxy.common.handler;

import com.cubic.proxy.common.server.MessageProcessor;

/**
 * @ClassName MessgaeProcess
 * @Author QIANGLU
 * @Date 2020/3/19 11:17 上午
 * @Version 1.0
 */
public interface ServerMessgaeProcess extends MessageProcessor {

    public boolean ackSync(String msg);
}
