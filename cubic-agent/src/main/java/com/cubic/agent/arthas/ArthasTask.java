package com.cubic.agent.arthas;


import com.google.common.util.concurrent.ListenableFuture;
import com.cubic.agent.factory.Task;
import com.cubic.agent.remote.ResponseHandler;
import com.cubic.agent.arthas.telnet.Telnet;
import com.cubic.agent.arthas.telnet.TelnetCommand;
import com.cubic.agent.arthas.telnet.TelnetStore;
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
public class ArthasTask implements Task {

    private static final Logger log = LoggerFactory.getLogger(ArthasTask.class);

    private final String id;

    private final String pid;

    private final TelnetStore telnetStore;

    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(10), new DefaultThreadFactory("arthasTask-"));

    private volatile ListenableFuture<Integer> future;

    private final String command;

    private final ResponseHandler handler;

    public ArthasTask(String id, TelnetStore telnetStore, String pid, String command, ResponseHandler handler) {
        this.id = id;
        this.telnetStore = telnetStore;
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
            Telnet telnet = null;
            try {
                telnet = telnetStore.getTelnet(pid);

                telnet.write(command);
                telnet.read(command, handler);
            } catch (Exception e) {
                log.error("arthas task execute command error:{}", e);
            } finally {
                if (telnet != null) {
                    telnet.close();
                }
            }
        });
    }

    private boolean actShutDownCommand() {
        if (!TelnetCommand.SHUTDOWN_COMMAND.equals(command)) {
            return false;
        }

        Telnet client = null;
        try {
            client = telnetStore.tryGetTelnet();
            if (client != null) {
                client.write(TelnetCommand.SHUTDOWN_COMMAND);
            }
        } catch (Exception e) {
            // ignore
        } finally {
            if (client != null) {
                client.close();
            }
        }

        return true;
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
