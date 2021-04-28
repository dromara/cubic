package com.cubic.agent.cmd.jvm.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * 命令修改线程池参数
 *
 * @author zhanghao
 * @date 2021/4/59:46 上午
 */
public enum ThreadPoolCommandItems {
    /**
     * 修改核心线程数
     */
    CORE_POOL_SIZE("setCorePoolSize", (t, v) ->
            t.setCorePoolSize(doubleToObject((Double) v, Integer.class))
    ),

    /**
     * 修改存活时间
     */
    KEEP_ALIVE_TIME("setKeepAliveTime", (t, v) ->
            t.setKeepAliveTime(doubleToObject((Double) v, Long.class), TimeUnit.MILLISECONDS)
    ),

    /**
     * 修改最大线程数
     */
    MAXIMUM_POOL_SIZE("setMaximumPoolSize", (t, v) ->
            t.setMaximumPoolSize(doubleToObject((Double) v, Integer.class))
    ),

    /**
     * 修改拒绝策略
     */
    REJECTED_EXECUTION_HANDLER("setRejectedExecutionHandler", (t, v) -> {
        for (RejectedExecutionHandler rejectedExecutionHandler : RejectedExecution.HANDLERS) {
            if (rejectedExecutionHandler.getClass().getName().split("\\$")[1].equals(v.toString())) {
                t.setRejectedExecutionHandler(rejectedExecutionHandler);
                return;
            }
        }
        throw new IllegalArgumentException("not found `RejectedExecutionHandler` for [" + v + "]");
    }),

    /**
     * 修改创建线程工厂
     */
    THREAD_FACTORY("setThreadFactory", (t, v) -> {
        throw new UnsupportedOperationException("Unsupported Sets the thread factory");
    });

    // 方法名
    private String methodName;
    // 赋值函数
    private BiConsumer<ThreadPoolExecutor, Object> func;


    ThreadPoolCommandItems(String methodName, BiConsumer<ThreadPoolExecutor, Object> func) {
        this.methodName = methodName;
        this.func = func;
    }

    /**
     * 修改数据参数
     *
     * @param tpe        线程池
     * @param methodName 方法名
     * @param arg        参数
     */
    public static void setItem(ThreadPoolExecutor tpe, String methodName, Object arg) {
        for (ThreadPoolCommandItems item : values()) {
            if (item.methodName.equals(methodName)) {
                item.func.accept(tpe, arg);
                return;
            }
        }
    }

    /**
     * dubbo转指定类型
     * <p>
     * gson数值默认转成double类型
     *
     * @param n
     * @param mClass
     * @param <T>
     * @return
     */
    private static <T> T doubleToObject(Double n, Class<T> mClass) {
        if (n == null || mClass == null) {
            return (T) n;
        }
        if (mClass == Integer.class) {
            return (T) (Integer) n.intValue();
        }

        if (mClass == Long.class) {
            return (T) (Long) n.longValue();
        }
        throw new UnsupportedOperationException("can not to [" + mClass + "]");
    }

    /**
     * 线程池拒绝策略
     */
    static class RejectedExecution {
        /**
         * 策略
         */
        private final static RejectedExecutionHandler[] HANDLERS = {
                /**
                 * 丢弃任务并抛出RejectedExecutionException异常
                */
                new ThreadPoolExecutor.AbortPolicy(),
                /**
                 * 由调用线程（提交任务的线程）处理该任务
                */
                new ThreadPoolExecutor.CallerRunsPolicy(),
                /**
                 * 丢弃队列最前面的任务，然后重新提交被拒绝的任务
                */
                new ThreadPoolExecutor.DiscardOldestPolicy(),
                /**
                 * 丢弃任务，但是不抛出异常
                */
                new ThreadPoolExecutor.DiscardPolicy()
        };
    }
}
