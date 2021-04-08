package com.cubic.agent.arthas.telnet;

/**
 * @ClassName TelnetStore
 * @Author QIANGLU
 * @Date 2020/4/21 10:42 上午
 * @Version 1.0
 */
public interface TelnetStore {

    Telnet getTelnet(String pid) throws Exception;

    Telnet tryGetTelnet() throws Exception;
}
