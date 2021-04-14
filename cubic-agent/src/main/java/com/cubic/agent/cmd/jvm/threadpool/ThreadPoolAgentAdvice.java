package com.cubic.agent.cmd.jvm.threadpool;

import net.bytebuddy.asm.Advice;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 字节码增强切入点
 *
 * @author zhanghao
 * @date 2021/4/62:09 下午
 */
public class ThreadPoolAgentAdvice {

    /**
     * 将线程池对象添加到监控中
     * <p>
     * 切入点在{@link ThreadPoolAgent}
     * <p>
     * 不能直接使用{@link ThreadPoolService#addMonitor(ThreadPoolExecutor)}原因，
     * 当前类是系统加载器加载，ThreadPoolMonitorService是app加载器加载，父加载器找不到子加载器
     * 加载的class，通过反射方式解决。
     * <p>
     * 同issues: https://github.com/raphw/byte-buddy/issues/984
     *
     * @param obj
     */
    @Advice.OnMethodExit
    public static void exit(@Advice.This Object obj) {
        if (obj instanceof ThreadPoolExecutor) {
            try {
                Class<?> clazz = Thread.currentThread()
                        .getContextClassLoader()
                        .loadClass("com.cubic.agent.cmd.jvm.threadpool.ThreadPoolService");
                clazz.getMethod("addMonitor", ThreadPoolExecutor.class).invoke(clazz.newInstance(), obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
