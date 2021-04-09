package com.cubic.agent.remote;

/**
 * @ClassName AgentChannelListener
 * @Author QIANGLU
 * @Date 2020/6/8 4:04 下午
 * @Version 1.0
 */
public interface AgentChannelListener {

    /**
     * 状态变化
     * @param status
     */
    void statusChanged(ChannelStatus status);
}
