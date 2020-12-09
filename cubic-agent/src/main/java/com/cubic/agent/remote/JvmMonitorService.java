
package com.cubic.agent.remote;

import com.cubic.agent.boot.CommonService;
import com.cubic.agent.boot.DefaultService;
import com.cubic.agent.boot.ServiceManager;
import com.cubic.agent.module.Message;
import com.google.gson.Gson;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@DefaultService
public class JvmMonitorService implements CommonService, AgentChannelListener, Runnable {
    private static final Logger logger = LoggerFactory.getLogger(JvmMonitorService.class);

    private volatile ScheduledFuture<?> sendMetricFuture;
    private volatile AgentNettyClient client;

    private volatile ChannelStatus status = ChannelStatus.DISCONNECT;


    @Override
    public void prepare() {
        ServiceManager.INMSTANCE.findService(AgentClientManager.class).addListener(this);

        sendMetricFuture = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("JvmMonitorService-")).scheduleAtFixedRate(this, 0L, 10, TimeUnit.SECONDS);

    }

    @Override
    public void start() {

    }


    @Override
    public void shutdown() {
        sendMetricFuture.cancel(true);
    }

    @Override
    public void complete() {

    }

    @Override
    public void statusChanged(ChannelStatus status) {
        if (ChannelStatus.CONNECTION == status) {
            client = ServiceManager.INMSTANCE.findService(AgentClientManager.class).getClient();
        }
        this.status = status;

    }

    @Override
    public void run() {
        if (ChannelStatus.CONNECTION == status) {

            try {
                Gson gson = new Gson();
                Message message = new Message(CommandCode.JVM.getCode(), "jvm monitor", "0000");


                client.getChannel().writeAndFlush(gson.toJson(message)).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (!future.isSuccess()) {
                            logger.error("JvmMonitorService send {} fail", message.getCommand());
                        } else {
                            logger.debug("JvmMonitorService send h:{}  channel : {} ", message.getCommand(), client.getChannel());
                        }
                    }
                });

            } catch (Exception e) {
                logger.warn("JvmMonitorService execute fail.{}", e.getMessage());
            }

        }
    }


}
