

package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cubic.proxy.common.constant.CommandCode;
import com.cubic.serialization.agent.v1.CommonMessage;
import com.matrix.proxy.entity.Information;
import com.matrix.proxy.mapper.InformationMapper;
import com.matrix.proxy.module.Message;
import com.cubic.proxy.common.server.ServerConnectionStore;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProxyHeartbeatProcessor extends DefaultMessageProcess {

    private static final Logger logger = LoggerFactory.getLogger(ProxyHeartbeatProcessor.class);

    @Resource
    private InformationMapper InformationMapper;
    private final String heartbeatResponse = initHeartbeatResponse();

    public ProxyHeartbeatProcessor() {
    }

    @Override
    public Integer code() {
        return CommandCode.HEARTBEAT.getCode();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void process(ChannelHandlerContext ctx, CommonMessage message) {
        if (logger.isDebugEnabled()) {
            logger.info("receive  client heartbeat, {}", message);
        }
        updateHeardBeat(message.getInstanceUuid());
        ctx.channel().writeAndFlush(heartbeatResponse);
    }

    public void updateHeardBeat(String instanceId) {
        Information information = Information.builder().lastHeartbeat(new Date()).build();
        UpdateWrapper<Information> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("instance_id",instanceId);
        InformationMapper.update(information,updateWrapper);
    }


    private String initHeartbeatResponse() {
        Map<String, Object> result = new HashMap<>(16);
        result.put("code", CommandCode.HEARTBEAT.getCode());
        result.put("command", "heartbeat");
        return JSON.toJSONString(result);
    }
}
