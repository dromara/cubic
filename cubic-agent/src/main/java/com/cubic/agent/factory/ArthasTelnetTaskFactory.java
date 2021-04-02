package com.cubic.agent.factory;


import com.cubic.agent.remote.ResponseHandler;
import com.cubic.agent.arthas.telnet.ArthasTelnetStore;
import com.cubic.agent.arthas.telnet.TelnetStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName ArthasTask
 * @Author QIANGLU
 * @Date 2020/4/21 10:17 上午
 * @Version 1.0
 */
public class ArthasTelnetTaskFactory implements TaskFactory<String> {

    private static final Logger log = LoggerFactory.getLogger(ArthasTelnetTaskFactory.class);

    private final static TelnetStore TELNET_STORE = ArthasTelnetStore.getInstance();

    private static final ArthasTelnetTaskFactory FACTORY = new ArthasTelnetTaskFactory();

    public static ArthasTelnetTaskFactory getInstance() {
        return FACTORY;
    }

    @Override
    public Integer code() {
        return 0;
    }

    @Override
    public String name() {
        return "arthas";
    }

    @Override
    public Task create(String id, String command, String pid, ResponseHandler handler) {
        return new ArthasTelnetTask(id, TELNET_STORE, pid,command,handler);
    }

}
