package com.matrix.proxy.service;

import com.cubic.proxy.common.server.ServerConnection;
import com.cubic.proxy.common.server.ServerConnectionStore;
import com.cubic.proxy.common.server.SyncFuture;
import com.cubic.serialization.agent.v1.CommonMessage;
import com.matrix.proxy.server.process.DefaultMessageProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @ClassName JdkCommandServiceImpl
 * @Author QIANGLU
 * @Date 2020/3/23 5:07 下午
 * @Version 1.0
 */
@Slf4j
@Service("jdkCommandService")
public class JdkCommandServiceImpl extends DefaultMessageProcess implements JdkCommandService {

    @Resource
    private ServerConnectionStore serverConnectionStore;

    public JdkCommandServiceImpl() {
    }

    /**
     * 获取应用当前参数信息
     *
     * @param instanceUuid
     * @return
     */
    @Override
    public String exeCommand(String instanceUuid, Integer type, String data) {
        CommonMessage commonMessage = CommonMessage.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setCode(type)
                .setCommand(data)
                .setInstanceUuid(instanceUuid).build();
        Optional<ServerConnection> connection = this.serverConnectionStore.getConnection(commonMessage.getInstanceUuid());
        if (connection.isPresent() && connection.get().isActive()) {
            try {
                SyncFuture syncFuture = new SyncFuture();
                putSyncFuture(commonMessage.getId(),syncFuture);
                log.info("执行reqId:{},uid:{}命令code:{}", commonMessage.getId(), commonMessage.getInstanceUuid(), commonMessage.getCode());
                return connection.get().write(commonMessage, syncFuture);
            } catch (Exception e) {
                log.error("CommandService dispose error: {}", e);
            }
        }
        Map<String, ServerConnection> connectionMap = serverConnectionStore.getAgentConnection();
        StringBuilder builder = new StringBuilder();
        connectionMap.forEach((k,v) ->{
            builder.append(k).append("  ||  ");
        });
        log.warn("不能获取uid:{} 到有效连接. instanceUuid:{},type:{},data:{},serverConnectionStore:{}", commonMessage.getId(), instanceUuid, type,data, builder);
        return "";
    }
}
