package com.cubic.agent.cmd.jvm.threadpool;

import com.cubic.agent.boot.ServiceManager;
import com.cubic.agent.process.Processor;
import com.cubic.agent.remote.CommandCode;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 处理线程池下发参数
 *
 * @author zhanghao
 * @date 2021/4/1311:23 上午
 */
public class ThreadPoolProcessor implements Processor {
    private static final Logger log = LoggerFactory.getLogger(ThreadPoolProcessor.class);

    private final ThreadPoolService service;

    @Override
    public List<Integer> types() {
        return ImmutableList.of(CommandCode.JVM_THREAD_POOL.getCode());
    }

    public ThreadPoolProcessor() {
        service = ServiceManager.INSTANCE.findService(ThreadPoolService.class);
        if (service == null) {
            log.warn("ServiceManager not find service `ThreadPoolMonitorService.class`");
        }
    }

    @Override
    public void process(ChannelHandlerContext ctx, String id, String command) throws ClassNotFoundException {
        if (service == null) {
            log.error("service is null");
            return;
        }
        Gson gson = new Gson();
        ThreadPoolCommand threadPoolCommandBody = gson.fromJson(command, ThreadPoolCommand.class);

        String key = threadPoolCommandBody.getKey();
        String methodName = threadPoolCommandBody.getName();
        Object arg = threadPoolCommandBody.getArg();

        if (key == null || methodName == null || arg == null) {
            log.error("ThreadPoolProcessor command is null, key [{}], name [{}], arg [{}]", key, threadPoolCommandBody.getName(), threadPoolCommandBody.getArg());
        }

        ThreadPoolExecutor monitorThreadPool = service.getMonitor(threadPoolCommandBody.getKey());
        if (monitorThreadPool == null) {
            log.warn("thread pool is null, key [{}]", key);
        }

        ThreadPoolCommandItems.setItem(monitorThreadPool, methodName, arg);
    }
}
