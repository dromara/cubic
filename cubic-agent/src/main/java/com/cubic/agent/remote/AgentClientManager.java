package com.cubic.agent.remote;

import com.cubic.agent.boot.CommonService;
import com.cubic.agent.boot.DefaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AgentClientService
 * @Author QIANGLU
 * @Date 2020/6/9 9:53 上午
 * @Version 1.0
 */
@DefaultService
public class AgentClientManager implements CommonService {

    private static final Logger logger = LoggerFactory.getLogger(AgentClientManager.class);

    private static final ScheduledExecutorService FAIL_EXECUTOR = new ScheduledThreadPoolExecutor(1);
    private volatile AgentNettyClient client;

    private boolean start = false;

    private final List<AgentChannelListener> agentChannelListeners = new LinkedList<>();

    @Override
    public void prepare() {

    }

    public AgentNettyClient getClient() {
        return client;
    }

    public void addListener(AgentChannelListener listener) {
        agentChannelListeners.add(listener);
    }

    public void notifyService(ChannelStatus status) {

        for (AgentChannelListener listener : agentChannelListeners) {
            try {
                listener.statusChanged(status);
            } catch (Exception e) {
                logger.error("Fail to notify {} about channel connected.", listener.getClass().getName());
            }
        }
    }

    private void startFailoverTask() {

        FAIL_EXECUTOR.scheduleAtFixedRate(() -> {
            if (client != null && client.isRunning()) {
                return;
            }

            refreshClient();
        }, 0L, 10L, TimeUnit.SECONDS);
    }

    private void refreshClient() {
        logger.info("start cubic netty client");
        try {
            client = new AgentNettyClient();
            client.start();

            if (client.isRunning()) {
                notifyService(ChannelStatus.CONNECTION);
            } else {
                notifyService(ChannelStatus.DISCONNECT);

                logger.warn("cubic netty client start fail");
                logger.info("agent client start error will restart  10s...");
            }
        } catch (Throwable e) {
            logger.info("refresh cubic netty client fail", e);
        }
    }


    @Override
    public void start() {
        if (start) {
            return;
        }

        refreshClient();
        startFailoverTask();
        start = true;
    }

    @Override
    public void shutdown() {
        try {

            FAIL_EXECUTOR.shutdown();
            if (client != null) {
                client.destroyAndSync();
                notifyService(ChannelStatus.DISCONNECT);
            }
        } catch (Exception e) {
            logger.error("cubic agent shutdown error", e);

        }
    }

    @Override
    public void complete() {

    }
}
