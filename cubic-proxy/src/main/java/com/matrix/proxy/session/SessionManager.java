package com.matrix.proxy.session;

import com.matrix.proxy.server.ServerConnection;
import com.matrix.proxy.webscoket.WebConnection;

/**
 * @ClassName SessionManager
 * @Author QIANGLU
 * @Date 2020/4/22 2:45 下午
 * @Version 1.0
 */
public interface SessionManager {

    Session create(WebConnection webConnection, ServerConnection serverConnection);

    Session getSession(String id);
}
