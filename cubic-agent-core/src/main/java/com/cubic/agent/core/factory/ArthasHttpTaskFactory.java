package com.cubic.agent.core.factory;


import com.cubic.agent.core.arthas.ArthasClientStore;
import com.cubic.agent.core.arthas.ArthasStore;
import com.cubic.agent.core.remote.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName ArthasTask
 * @Author QIANGLU
 * @Date 2020/4/21 10:17 上午
 * @Version 1.0
 */
public class ArthasHttpTaskFactory implements TaskFactory<String> {

    private static final Logger log = LoggerFactory.getLogger(ArthasHttpTaskFactory.class);

    private final static ArthasStore CLIENT_STORE = ArthasClientStore.getInstance();

    private static final ArthasHttpTaskFactory FACTORY = new ArthasHttpTaskFactory();

    public static ArthasHttpTaskFactory getInstance() {
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
        return new ArthasHttpTask(id, CLIENT_STORE, pid,command,handler);
    }

}
