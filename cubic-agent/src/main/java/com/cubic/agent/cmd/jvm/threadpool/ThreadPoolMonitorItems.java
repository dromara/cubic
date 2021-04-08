package com.cubic.agent.cmd.jvm.threadpool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 监控线程池参数
 *
 * @author zhanghao
 * @date 2021/4/59:46 上午
 */
public enum ThreadPoolMonitorItems {
    /**
     * 活动线程数
     */
    ACTIVE_COUNT((t) -> t.getActiveCount()),
    /**
     * 线程数
     */
    POOL_SIZE((t) -> t.getPoolSize()),
    /**
     * 核心线程数
     */
    CORE_POOL_SIZE((t) -> t.getCorePoolSize()),
    /**
     * 存活时间
     */
    KEEP_ALIVE_TIME((t) -> t.getKeepAliveTime(TimeUnit.MILLISECONDS)),
    /**
     * 完成任务数
     */
    COMPLETED_TASK_COUNT((t) -> t.getCompletedTaskCount()),

    /**
     * 最大时的线程数
     */
    LARGEST_POOL_SIZE((t) -> t.getLargestPoolSize()),
    /**
     * 最大线程数
     */
    MAXIMUM_POOL_SIZE((t) -> t.getMaximumPoolSize()),
    /**
     * 计划执行的任务数
     */
    TASK_COUNT((t) -> t.getTaskCount());

    private Function<ThreadPoolExecutor, Object> func;

    ThreadPoolMonitorItems(Function<ThreadPoolExecutor, Object> func) {
        this.func = func;
    }

    /**
     * 采集数据
     *
     * @param tpe 线程池
     * @return
     */
    public static Map<String, Object> getItems(ThreadPoolExecutor tpe) {
        Map<String, Object> items = new HashMap<>();
        for (ThreadPoolMonitorItems items1 : values()) {
            items.put(items1.name(), items1.func.apply(tpe));
        }
        return items;
    }

    /**
     * 线程池唯一键
     * <p>
     * 线程名 + 创建线程栈 + 方法 + hashcode
     *
     * @return
     */
    public static String key() {
        String name = Thread.currentThread().getName();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = stackTrace[stackTrace.length - 1];
        StringBuilder builder = new StringBuilder(name);
        builder.append("-")
                .append(stackTraceElement.getClassName())
                .append("[")
                .append(stackTraceElement.getMethodName())
                .append("]")
                .append(stackTraceElement.hashCode());
        return builder.toString();
    }
}
