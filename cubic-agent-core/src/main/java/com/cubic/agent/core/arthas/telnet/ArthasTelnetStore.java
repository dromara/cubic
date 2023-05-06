package com.cubic.agent.core.arthas.telnet;

import org.apache.commons.net.telnet.TelnetClient;

/**
 * @ClassName ArthasTelnet
 * @Author QIANGLU
 * @Date 2020/4/21 10:50 上午
 * @Version 1.0
 */
public class ArthasTelnetStore extends AbstractTelnetStore {

    private final static AbstractTelnetStore STORE = new ArthasTelnetStore();

    public static AbstractTelnetStore getInstance(){
        return STORE;
    }

    @Override
    public Telnet doCreateTelnet(TelnetClient client){
        return new ArthasTelnet(client);
    }

}
