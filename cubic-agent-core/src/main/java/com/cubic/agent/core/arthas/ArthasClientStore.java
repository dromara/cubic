package com.cubic.agent.core.arthas;

import com.cubic.agent.core.arthas.telnet.*;
import com.cubic.agent.core.arthas.telnet.AbstractTelnetStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName ArthasTelnet
 * @Author QIANGLU
 * @Date 2020/4/21 10:50 上午
 * @Version 1.0
 */
public class ArthasClientStore implements ArthasStore {

    private static final Logger log = LoggerFactory.getLogger(AbstractTelnetStore.class);

    private final static ArthasClientStore STORE = new ArthasClientStore();

    public static ArthasClientStore getInstance(){
        return STORE;
    }

    private ArthasClient arthasClient;



    private void initCreateClient(String pid) throws Exception {
        ArthasClient arthasEntity = new ArthasClient(pid);
        arthasEntity.start();
        this.arthasClient = arthasEntity;
    }


    @Override
    public synchronized ArthasClient getClient(String pid) {

        try {
            if (arthasClient == null || !arthasClient.getPid().equals(pid)) {
                initCreateClient(pid);
            }
            return arthasClient;

        } catch (Exception e) {
            log.warn("can not init arthas telnet", e);
        }

        return this.arthasClient;
    }
}
