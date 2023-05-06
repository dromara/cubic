package com.cubic.agent.core.factory;


import com.cubic.agent.core.arthas.ArthasClient;
import com.cubic.agent.core.arthas.ArthasStore;
import com.cubic.agent.core.remote.CommandCode;
import com.cubic.agent.core.remote.ResponseHandler;
import com.google.common.util.concurrent.ListenableFuture;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ArthasTask
 * @Author QIANGLU
 * @Date 2020/4/21 10:17 上午
 * @Version 1.0
 */
public class ArthasHttpTask implements Task {

    private static final Logger log = LoggerFactory.getLogger(ArthasHttpTask.class);

    private final String id;

    private final String pid;

    private final ArthasStore arthasStore;

    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(10), new DefaultThreadFactory("arthasTask-"));

    private volatile ListenableFuture<Integer> future;

    private final String command;

    private final ResponseHandler handler;

    public ArthasHttpTask(String id, ArthasStore arthasStore, String pid, String command, ResponseHandler handler) {
        this.id = id;
        this.arthasStore = arthasStore;
        this.pid = pid;
        this.command = command;
        this.handler = handler;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * 获取telnet 执行命令
     *
     * @return
     */
    @Override
    public void execute() {

        EXECUTOR_SERVICE.execute(() -> {

            ArthasClient client = null;
            try {
                client = arthasStore.getClient(pid);
                client.start();
            } catch (Exception e) {
                log.error("arthas task execute command error:{}", e);
                handler.write(CommandCode.ARTHAS_START.getCode(), "fail");
            } finally {
                handler.write(CommandCode.ARTHAS_START.getCode(), "succ");
            }
        });
    }


    @Override
    public void cancel() {

        try {
            if (future != null) {
                future.cancel(true);
                future = null;
            }
        } catch (Exception e) {
            log.warn("future cacel fail, e:{}", e);
        }

    }

}
