package com.cubic.agent.arthas;

import com.cubic.agent.boot.AgentPackagePath;
import com.cubic.agent.conf.AgentConfig;
import com.cubic.agent.utils.VmUtils;
import com.sun.tools.attach.AgentLoadException;
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
        }catch (AgentLoadException e){
            logger.error("加载 arthas agent jar 失败 ，请检查agent.config 中 agent.arthas_path 路径 {} 是否配置正确",AgentConfig.Agent.ARTHAS_PATH);
            throw e;
        }finally {
            if (virtualMachine != null) {
                virtualMachine.detach();
            }
        }
    }
}
