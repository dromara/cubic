package com.cubic.agent.cmd.jvm.threadpool;

import com.cubic.agent.boot.CommonService;
import com.cubic.agent.boot.ServiceManager;
import com.cubic.agent.conf.AgentConfig;
import com.cubic.agent.remote.*;
import com.cubic.serialization.agent.v1.CommonMessage;
import com.cubic.serialization.agent.v1.JVMThreadPoolMetric;
import com.cubic.serialization.agent.v1.ThreadPoolInfo;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池监控服务
 *
 * @author zhanghao
 * @date 2021/4/59:00 上午
 */
public class ThreadPoolService implements CommonService, AgentChannelListener {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolService.class);

    private volatile ChannelStatus status = ChannelStatus.DISCONNECT;
    private volatile AgentNettyClient client;

    private ScheduledThreadPoolExecutor scheduledExecutor;
    private ScheduledFuture<?> sendMetricFuture;
    /**
     * 采集间隔时间10s
     */
    private final static Long PERIOD = 60L;

    /**
     * 所有线程池资源的引用
     * <p>
     * key规则 -> {@link ThreadPoolMonitorItems#key()}
     * value  -> 弱引用线程池资源，不影响gc回收
     */
    private static Map<String, WeakReference<ThreadPoolExecutor>> threadPoolReferences = new ConcurrentHashMap<>();


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
        scheduledExecutor = new ScheduledThreadPoolExecutor(1,
                new DefaultThreadFactory("JvmThreadPoolMonitorService-")
        );
        sendMetricFuture = scheduledExecutor.scheduleAtFixedRate(new ThreadPoolMonitor(), 0L, PERIOD, TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        if (sendMetricFuture != null) {
            sendMetricFuture.cancel(true);
        }
        if (scheduledExecutor != null) {
            scheduledExecutor.shutdown();
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
        threadPoolReferences.put(ThreadPoolMonitorItems.key(threadPoolExecutor), new WeakReference<>(threadPoolExecutor));
    }

    /**
     * 获取监控线程池
     *
     * @param key
     * @return
     */
    public ThreadPoolExecutor getMonitor(String key) {
        WeakReference<ThreadPoolExecutor> threadPoolExecutorWeakReference = threadPoolReferences.get(key);
        if (threadPoolExecutorWeakReference == null) {
            return null;
        }
        return threadPoolExecutorWeakReference.get();
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
            JVMThreadPoolMetric.Builder jvmThreadPoolMap = JVMThreadPoolMetric.newBuilder();
            threadPoolReferences.forEach((key, weakThreadPool) -> {
                if (weakThreadPool == null || weakThreadPool.get() == null) {
                    return;
                }
                jvmThreadPoolMap.putThreadPoolMetric(key, ThreadPoolInfo.newBuilder()
                                .putAllThreadPoolMetricInfo(ThreadPoolMonitorItems.getItems(weakThreadPool.get()))
                                .build());
            });
            try {
                CommonMessage commonMessage = CommonMessage.newBuilder()
                        .setBody("JVM Thread Pool")
                        .setCode(CommandCode.JVM_THREAD_POOL.getCode())
                        .setMsgContent(jvmThreadPoolMap.build().toByteString())
                        .setId("0000")
                        .setInstanceUuid(AgentConfig.Agent.INSTANCE_UUID)
                        .setInstanceName(AgentConfig.Agent.SERVICE_NAME)
                        .setInstanceVersion(AgentConfig.Agent.VERSION).build();
                client.getChannel().writeAndFlush(commonMessage).addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        logger.error("ThreadPoolMonitorService send {} fail", commonMessage.getCommand());
                    } else {
                        logger.debug("ThreadPoolMonitorService send command:{}  channel : {} ",
                                commonMessage.getCommand(), client.getChannel());
                    }
                });
            } catch (Exception e) {
                logger.warn("ThreadPoolMonitorService execute fail. " + e.getMessage(), e);
            }

        }
    }

}
