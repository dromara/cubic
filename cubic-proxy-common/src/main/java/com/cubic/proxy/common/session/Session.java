package com.cubic.proxy.common.session;

import com.cubic.proxy.common.server.ServerConnection;
import com.cubic.proxy.common.webserver.WebConnection;

/**
 * @ClassName Session
 * @Author QIANGLU
 * @Date 2020/4/22 2:46 下午
 * @Version 1.0
 */
public interface Session {

    enum State {
        finish, down
    }

    void writeToAgent(String data);

    void writeToWeb(String data);

    WebConnection getWebConnection();

    ServerConnection getServerConnection();

    String getId();
}
