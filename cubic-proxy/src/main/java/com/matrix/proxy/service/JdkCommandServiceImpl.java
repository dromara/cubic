package com.matrix.proxy.service;

import com.alibaba.fastjson.JSON;
import com.matrix.proxy.module.Command;
import com.matrix.proxy.server.ServerConnection;
import com.matrix.proxy.server.ServerConnectionStore;
import com.matrix.proxy.server.SyncFuture;
import com.matrix.proxy.server.process.DefaultMessageProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

        Command.CommandBuilder commandBuilder = Command.builder();
        Command command =  commandBuilder.id(UUID.randomUUID().toString()).code(type).command(data).instanceUuid(instanceUuid).build();
        Optional<ServerConnection> connection = this.serverConnectionStore.getConnection(command.getInstanceUuid());

        if (connection.isPresent() && connection.get().isActive()) {
            try {
                SyncFuture syncFuture = new SyncFuture();
                putSyncFuture(command.getId(),syncFuture);
                log.info("执行reqId:{},uid:{}命令code:{}", command.getId(), command.getInstanceUuid(), command.getCode());
                return connection.get().write(JSON.toJSONString(command), syncFuture);
            } catch (Exception e) {
                log.error("CommandService dispose error:{}", e);
            }
        }
        log.warn("不能获取uid:{}到有效连接:{}", command.getId(), command.getCode());

        return "";
    }


}
