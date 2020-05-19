package com.matrix.agent.arthas;


import com.matrix.agent.ResponseHandler;
import com.matrix.agent.factory.Task;
import com.matrix.agent.factory.TaskFactory;
import com.matrix.agent.telnet.ArthasTelnetStore;
import com.matrix.agent.telnet.TelnetStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName ArthasTask
 * @Author QIANGLU
 * @Date 2020/4/21 10:17 上午
 * @Version 1.0
 */
public class ArthasTaskFactory implements TaskFactory<String> {

    private static final Logger log = LoggerFactory.getLogger(ArthasTaskFactory.class);

    private final static TelnetStore TELNET_STORE = ArthasTelnetStore.getInstance();

    private static final ArthasTaskFactory FACTORY = new ArthasTaskFactory();

    public static ArthasTaskFactory getInstance() {
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
        return new ArthasTask(id, TELNET_STORE, pid,command,handler);
    }

}
