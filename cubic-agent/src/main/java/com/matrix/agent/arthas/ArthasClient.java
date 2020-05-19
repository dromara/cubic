package com.matrix.agent.arthas;

import com.matrix.cubic.agent.core.conf.AgentConfig;
import com.matrix.cubic.agent.core.utils.VmUtils;
import com.sun.tools.attach.VirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @ClassName ArthasClient
 * @Author QIANGLU
 * @Date 2020/4/21 3:04 下午
 * @Version 1.0
 */
public class ArthasClient {
    private static final Logger logger = LoggerFactory.getLogger(ArthasClient.class);

    private final String pid;

    public ArthasClient(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public void start() throws Exception {
        logger.info("start attach to arthas agent");
        VirtualMachine virtualMachine = null;
        try {
            virtualMachine = VmUtils.getVirtualMachine(pid);
            logger.info("start load arthas agent, load {}", AgentConfig.Agent.ARTHAS_PATH);
            virtualMachine.loadAgent(AgentConfig.Agent.ARTHAS_PATH,
                    "arthas");
        } finally {
            if (virtualMachine != null) {
                virtualMachine.detach();
            }
        }
    }
}
