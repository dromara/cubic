package com.cubic.proxy.common.session;

import com.cubic.proxy.common.server.ServerConnection;
import com.cubic.proxy.common.webserver.WebConnection;
import com.cubic.serialization.agent.v1.CommonMessage;

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

    void writeToAgent(CommonMessage data);

    void writeToWeb(CommonMessage data);

    WebConnection getWebConnection();

    ServerConnection getServerConnection();

    String getId();
}
