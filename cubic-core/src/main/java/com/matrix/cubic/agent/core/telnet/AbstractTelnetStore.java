package com.matrix.cubic.agent.core.telnet;


import com.matrix.cubic.agent.core.arthas.ArthasClient;
import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName AbstractTelnetStore
 * @Author QIANGLU
 * @Date 2020/4/21 10:50 上午
 * @Version 1.0
 */
public abstract class AbstractTelnetStore implements TelnetStore {

    private static final Logger log = LoggerFactory.getLogger(AbstractTelnetStore.class);


    private ArthasClient arthasClient;


    @Override
    public Telnet getTelnet(String pid) throws Exception {


        TelnetClient client = doGetTelnet(pid);
        return doCreateTelnet(client);
    }



    @Override
    public Telnet tryGetTelnet() throws Exception {
        return null;
    }

    protected abstract Telnet doCreateTelnet(TelnetClient client);


    private synchronized TelnetClient doGetTelnet(String pid) {

        TelnetClient client = createClient();
        if (client != null) {
            return client;
        }

        try {
            if (arthasClient == null || !arthasClient.getPid().equals(pid)) {
                return initCreateClient(pid);
            } else {
                return createClient();
            }
        } catch (Exception e) {
            resetClient();
            throw new IllegalStateException("can not init arthas telnet, " + e.getMessage(), e);

        }
    }

    private TelnetClient initCreateClient(String pid) throws Exception {
        ArthasClient arthasEntity = new ArthasClient(pid);
        arthasEntity.start();
        TelnetClient client = createClient();
        this.arthasClient = arthasEntity;
        return client;
    }

    private void resetClient() {
        this.arthasClient = null;
    }

    private TelnetClient createClient() {

        try {
            TelnetClient client = new TelnetClient();
            client.setConnectTimeout(TelnetConstants.TELNET_CONNECT_TIMEOUT);
            client.connect(TelnetConstants.TELNET_CONNECTION_IP, TelnetConstants.TELNET_CONNECTION_PORT);
            return client;
        } catch (Exception e) {
            log.error("can not connection 3658 port");
            resetClient();
            return null;
        }

    }
}
