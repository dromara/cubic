package com.cubic.agent.cmd.jvm.threadpool;

import com.cubic.agent.boot.CommonService;
import com.cubic.agent.boot.ServiceManager;
import com.cubic.agent.module.Message;
import com.cubic.agent.remote.*;
import com.google.gson.Gson;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池监控服务
 *
 * @author zhanghao
 * @date 2021/4/59:00 上午
 */
public class ThreadPoolMonitorService implements CommonService, AgentChannelListener {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolMonitorService.class);

    private volatile ChannelStatus status = ChannelStatus.DISCONNECT;
    private volatile AgentNettyClient client;

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private ScheduledFuture<?> sendMetricFuture;


    /**
     * 所有线程池资源的引用
     * <p>
     * key规则 -> {@link ThreadPoolMonitorItems#key()}
     * value  -> 弱引用线程池资源，不影响gc回收
     */
    public static Map<String, WeakReference<ThreadPoolExecutor>> threadPoolReferences = new ConcurrentHashMap<>();


    @Override
    public void prepare() {
        ServiceManager.INSTANCE.findService(AgentClientManager.class).addListener(this);
        /**
         * 初始化，对监控类进行字节码增强
         */
        ThreadPoolAgent.init(ServiceManager.instrumentation);
    }

    @Override
    public void start() {
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("JvmThreadPoolMonitorService-"));
        sendMetricFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate(new ThreadPoolMonitor(), 0L, 10, TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        if (sendMetricFuture != null) {
            sendMetricFuture.cancel(true);
        }
        if (scheduledThreadPoolExecutor != null) {
            scheduledThreadPoolExecutor.shutdown();
        }
    }

    @Override
    public void complete() {

    }

    /**
     * 添加监控线程池
     *
     * @param threadPoolExecutor
     */
    public static void addMonitor(ThreadPoolExecutor threadPoolExecutor) {
        threadPoolReferences.put(ThreadPoolMonitorItems.key(), new WeakReference<>(threadPoolExecutor));
    }

    /**
     * 清理被回收的弱引用对象
     */
    public static void clean() {
        Iterator<Map.Entry<String, WeakReference<ThreadPoolExecutor>>> it = threadPoolReferences.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, WeakReference<ThreadPoolExecutor>> entry = it.next();
            if (entry.getValue() == null || entry.getValue().get() == null) {
                logger.debug("cubic thread pool monitor, clean key {}", entry.getKey());
                it.remove();
            }
        }
    }


    @Override
    public void statusChanged(ChannelStatus status) {
        if (ChannelStatus.CONNECTION == status) {
            client = ServiceManager.INSTANCE.findService(AgentClientManager.class).getClient();
        }
        this.status = status;
    }

    class ThreadPoolMonitor implements Runnable {

        @Override
        public void run() {
            clean();

            if (ChannelStatus.CONNECTION != status) {
                logger.warn("Channel not connection.");
                return;
            }

            Map<String, Map<String, Object>> body = new HashMap<>();
            threadPoolReferences.forEach((key, weakThreadPool) -> {
                if (weakThreadPool == null || weakThreadPool.get() == null) {
                    return;
                }
                body.put(key, ThreadPoolMonitorItems.getItems(weakThreadPool.get()));
            });

            if (body.isEmpty()) {
                logger.debug("cubic thread pool monitor, body is empty.");
                return;
            }

            try {
                Gson gson = new Gson();
                Message message = new Message(CommandCode.JVM_THREAD_POOL.getCode(), gson.toJson(body), "0000");
                client.getChannel().writeAndFlush(gson.toJson(message)).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (!future.isSuccess()) {
                            logger.error("ThreadPoolMonitorService send {} fail", message.getCommand());
                        } else {
                            logger.debug("ThreadPoolMonitorService send h:{}  channel : {} ", message.getCommand(), client.getChannel());
                        }
                    }
                });
            } catch (Exception e) {
                logger.warn("ThreadPoolMonitorService execute fail.{}", e.getMessage());
            }

        }
    }

}
