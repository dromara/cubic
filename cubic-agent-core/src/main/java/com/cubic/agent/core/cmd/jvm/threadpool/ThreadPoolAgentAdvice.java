package com.cubic.agent.core.cmd.jvm.threadpool;

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
     * 不能直接使用{@link ThreadPoolService#addMonitor(ThreadPoolExecutor)}原因:
     * 由于该段代码插入到{@link ThreadPoolExecutor}中，所以对应类加载器为Bootstrap classloader.
     * 而 {@link ThreadPoolService}是通过自定义类加载器加载，因此无法直接访问。
     * 故通过SystemClassLoader经由MatrixAgent获取自定义自定义ClassLoader，然后反射调用即可。
     * <p>
     * 同issues: https://github.com/raphw/byte-buddy/issues/984
     *
     * @param obj
     *
     */
    @Advice.OnMethodExit
    public static void exit(@Advice.This Object obj) {
        if (obj instanceof ThreadPoolExecutor) {
            try {
                Class<?> matrixAgentClass= Thread.currentThread().getContextClassLoader().loadClass("com.cubic.agent.MatrixAgent");

                ClassLoader matrixAgentClassLoader = (ClassLoader)(matrixAgentClass.getMethod("getMatrixClassLoader").invoke(null));

                Class<?> clazz = matrixAgentClassLoader
                        .loadClass("com.cubic.agent.core.cmd.jvm.threadpool.ThreadPoolService");
                clazz.getMethod("addMonitor", ThreadPoolExecutor.class).invoke(null, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
