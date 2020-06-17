package com.matrix.cubic.agent.core.arthas;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AgentRemotingExecutor
 * @Author QIANGLU
 * @Date 2020/4/23 11:49 上午
 * @Version 1.0
 */
public class AgentRemotingExecutor {

    private static final ListeningExecutorService executorService;

    static {
        int threadNum = Integer.parseInt(System.getProperty("sky.thread.num", "16"));
        executorService = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(threadNum,threadNum, 0L, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(200),new ThreadFactoryBuilder().setDaemon(true).setNameFormat("AgentRemoting-exec-%d").build()));
    }

    public static ListeningExecutorService getExecutor() {
        return executorService;
    }
}
