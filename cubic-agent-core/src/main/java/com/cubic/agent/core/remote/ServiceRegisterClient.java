package com.cubic.agent.core.remote;

import com.cubic.agent.core.boot.CommonService;
import com.cubic.agent.core.boot.DefaultService;
import com.cubic.agent.core.boot.ServiceManager;
import com.cubic.agent.core.conf.AgentConfig;
import com.cubic.agent.core.utils.OSUtil;
import com.cubic.serialization.agent.v1.CommonMessage;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ServiceRegisterClient
 * @Author QIANGLU
 * @Date 2020/6/8 4:25 下午
 * @Version 1.0
 */
@DefaultService
public class ServiceRegisterClient implements CommonService, AgentChannelListener, Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRegisterClient.class);

    private volatile ChannelStatus status = ChannelStatus.DISCONNECT;
    private volatile ScheduledFuture<?> scheduledFuture;
    public static volatile boolean registed = false;

    @Override
    public void prepare() {
        ServiceManager.INSTANCE.findService(AgentClientManager.class).addListener(this);
    }

    @Override
    public void start() {
        scheduledFuture = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("serviceRegisterClient")).scheduleAtFixedRate(this, 0L, 10, TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        scheduledFuture.cancel(true);
    }

    @Override
    public void complete() {

    }

    @Override
    public void statusChanged(ChannelStatus status) {
        if (ChannelStatus.DISCONNECT == status) {
            registed = false;
        }
        this.status = status;
    }

    @Override
    public void run() {
        if (ChannelStatus.CONNECTION == status) {
            try {
                CommonMessage.Builder commonMessage = CommonMessage.newBuilder()
                        .setId("0000")
                        .setBody("heart beat")
                        .setCode(CommandCode.HEARTBEAT.getCode())
                        .setInstanceName(AgentConfig.Agent.SERVICE_NAME)
                        .setInstanceUuid(AgentConfig.Agent.INSTANCE_UUID)
                        .setInstanceVersion(AgentConfig.Agent.VERSION);
                if (!registed) {
                    commonMessage.setCode(CommandCode.REGIST.getCode());
                    commonMessage.setBody("register");
                    commonMessage.putAllOsInfo(OSUtil.buildOSInfo());
                }
                AgentNettyClient client = ServiceManager.INSTANCE.findService(AgentClientManager.class).getClient();
                client.getChannel().writeAndFlush(commonMessage.build()).addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        logger.error("ServiceRegisterClient send {} fail", commonMessage.getBody());
                    } else {
                        logger.debug("send heartbeat:{} channel : {} ", commonMessage.getBody(), client.getChannel());
                    }
                });
            } catch (Exception e) {
                logger.warn("ServiceRegisterClient execute fail.{}", e.getMessage());
            }
        }
    }
}
