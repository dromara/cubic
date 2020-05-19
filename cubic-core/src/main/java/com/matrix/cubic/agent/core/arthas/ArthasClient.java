package com.matrix.cubic.agent.core.arthas;

import com.matrix.cubic.agent.core.boot.AgentPackagePath;
import com.matrix.cubic.agent.core.conf.AgentConfig;
import com.matrix.cubic.agent.core.utils.VmUtils;
import com.sun.tools.attach.VirtualMachine;
import org.apache.commons.lang3.StringUtils;
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

    private final static String DEFAULT_ARTHAS_FILE_NAME = "/arthas/arthas-agent.jar";


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
            String path = StringUtils.isEmpty(AgentConfig.Agent.ARTHAS_PATH) ? AgentPackagePath.getPath() + DEFAULT_ARTHAS_FILE_NAME : AgentConfig.Agent.ARTHAS_PATH;
            virtualMachine.loadAgent(path,
                    "arthas");
        } finally {
            if (virtualMachine != null) {
                virtualMachine.detach();
            }
        }
    }
}
