package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cubic.proxy.common.constant.CommandCode;
import com.cubic.proxy.common.module.Message;
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
    public void process(ChannelHandlerContext ctx, String datagram) {
        super.process(ctx, datagram);
        logger.info("receive datagram" + datagram);
        insertThreadPool(JSON.parseObject(datagram, Message.class));
    }

    private void insertThreadPool(Message message) {
        if (message == null) {
            return;
        }
        JSONObject body = JSON.parseObject(message.getBody());
        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        body.forEach((key, params) -> {
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
