package com.matrix.proxy.session;

import com.cubic.proxy.common.session.Session;
import com.cubic.proxy.common.webserver.WebConnection;
import com.google.common.util.concurrent.SettableFuture;
import com.cubic.proxy.common.server.ServerConnection;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName DefaultSession
 * @Author QIANGLU
 * @Date 2020/4/22 2:58 下午
 * @Version 1.0
 */
@Slf4j
public class DefaultSession implements Session {

    private final String id;

    private final WebConnection webConnection;

    private final ServerConnection serverConnection;

    private SettableFuture<State> future = SettableFuture.create();

    public DefaultSession(String id, WebConnection webConnection, ServerConnection serverConnection) {
        this.id = id;
        this.webConnection = webConnection;
        this.serverConnection = serverConnection;
    }

    @Override
    public void writeToAgent(String data) {
        serverConnection.write(data);
    }

    @Override
    public void writeToWeb(String data) {
        if(log.isDebugEnabled()){
            log.debug("DefaultSession will write data to web  ");

        }
        webConnection.write(new TextWebSocketFrame(data));
        log.info("DefaultSession write to web succ data length:{}");
    }

    @Override
    public WebConnection getWebConnection() {
        return webConnection;
    }

    @Override
    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    @Override
    public String getId() {
        return id;
    }
}
