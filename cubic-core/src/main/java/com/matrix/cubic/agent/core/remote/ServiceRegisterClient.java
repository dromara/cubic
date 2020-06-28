package com.matrix.cubic.agent.core.remote;

import com.google.gson.Gson;
import com.matrix.cubic.agent.core.boot.CommonService;
import com.matrix.cubic.agent.core.boot.DefaultService;
import com.matrix.cubic.agent.core.boot.ServiceManager;
import com.matrix.cubic.agent.core.module.Message;
import com.matrix.cubic.agent.core.utils.OSUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
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
        ServiceManager.INMSTANCE.findService(AgentClientService.class).addListener(this);
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
                Gson gson = new Gson();
                Message message = new Message(CommandCode.HEARTBEAT.getCode(), "heart beat", "0000");

                if (!registed) {
                    message.setCode(CommandCode.REGIST.getCode());
                    message.setBody("register");
                    message.setOsInfo(OSUtil.buildOSInfo());
                }
                AgentNettyClient client = ServiceManager.INMSTANCE.findService(AgentClientService.class).getClient();
                client.getChannel().writeAndFlush(gson.toJson(message)).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            logger.error("ServiceRegisterClient send {}fail", message.getBody());
                        } else {
                            logger.debug("send heartbeat:{}  channel : {} ", message.getBody(), client.getChannel());
                        }
                    }
                });

            } catch (Exception e) {
                logger.warn("ServiceRegisterClient execute fail.{}", e.getMessage());
            }

        }

    }


}
