package com.cubic.agent.remote;

import com.cubic.agent.boot.CommonService;
import com.cubic.agent.boot.DefaultService;
import com.cubic.agent.boot.ServiceManager;
import com.cubic.agent.conf.AgentConfig;
import com.cubic.agent.utils.OSUtil;
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
                CommonMessage.Builder commonMessage = CommonMessage.newBuilder();
                if (!registed) {
                    commonMessage.setId("0000");
                    commonMessage.setCode(CommandCode.REGIST.getCode());
                    commonMessage.setBody("register");
                    commonMessage.putAllOsInfo(OSUtil.buildOSInfo());
                    commonMessage.setInstanceName(AgentConfig.Agent.SERVICE_NAME);
                    commonMessage.setInstanceUuid(AgentConfig.Agent.INSTANCE_UUID);
                    commonMessage.setInstanceVersion(AgentConfig.Agent.VERSION);
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
