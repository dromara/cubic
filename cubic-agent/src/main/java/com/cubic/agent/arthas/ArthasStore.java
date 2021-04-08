package com.cubic.agent.arthas;

import com.cubic.agent.arthas.telnet.Telnet;

/**
 * @ClassName TelnetStore
 * @Author QIANGLU
 * @Date 2020/4/21 10:42 上午
 * @Version 1.0
 */
public interface ArthasStore {


    /**
     * 获取arthas client
     * @return
     */
    ArthasClient getClient(String pid);
}
