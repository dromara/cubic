package com.matrix.proxy.server.process;

import com.cubic.proxy.common.constant.CommandCode;
import com.cubic.serialization.agent.v1.CommonMessage;
import com.cubic.serialization.agent.v1.JVMThreadPoolMetric;
import com.google.protobuf.InvalidProtocolBufferException;
import com.matrix.proxy.entity.ThreadPoolEntity;
import com.matrix.proxy.mapper.ThreadPoolMapper;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 处理线程池信息
 *
 * @author zhanghao
 * @date 2021/4/611:49 上午
 */
@Service
public class JvmThreadPoolProcess extends DefaultMessageProcess {
    private static final Logger logger = LoggerFactory.getLogger(JvmThreadPoolProcess.class);

    @Resource
    private ThreadPoolMapper threadPoolMapper;

    @Override
    public Integer code() {
        return CommandCode.JVM_THREAD_POOL.getCode();
    }

    @Override
    public void process(ChannelHandlerContext ctx, CommonMessage datagram) {
        super.process(ctx, datagram);
        if(logger.isDebugEnabled()){
            logger.debug("receive datagram" + datagram);
        }
        insertThreadPool(datagram);
    }

    private void insertThreadPool(CommonMessage message) {
        if (message == null) {
            return;
        }
        JVMThreadPoolMetric body;
        try {
            body = JVMThreadPoolMetric.parseFrom(message.getMsgContent());
        } catch (InvalidProtocolBufferException e) {
            logger.error("反序列化线程池监控数据出错：", e);
            return;
        }
        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        body.getThreadPoolMetricMap().forEach((key, params) -> {
            threadPoolMapper.insert(ThreadPoolEntity.builder()
                    .instanceId(message.getInstanceUuid())
                    .instanceName(message.getInstanceName())
                    .threadPoolKey(key)
                    .threadPoolParams(params.toString())
                    .createTime(date)
                    .build());
        });
    }
}
