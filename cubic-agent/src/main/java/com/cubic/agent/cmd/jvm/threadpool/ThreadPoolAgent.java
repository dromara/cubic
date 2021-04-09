package com.cubic.agent.cmd.jvm.threadpool;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isConstructor;
import static net.bytebuddy.matcher.ElementMatchers.none;

/**
 * 通过字节码增强的方式监控线程池资源
 *
 * @author zhanghao
 * @date 2021/4/59:02 上午
 */
public class ThreadPoolAgent {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolAgent.class);


    public static void init(Instrumentation inst) {
        logger.debug("load cubic thread pool agent.");
        new AgentBuilder.Default()
                // 全部不忽略，默认会忽略引导类、jdk自带的类
                .ignore(none())
                .type(
                        ElementMatchers.named("java.util.concurrent.ThreadPoolExecutor")
                )

                .transform((builder, typeDescription, classLoader, module) -> {
                    builder = builder.visit(Advice
                            .to(ThreadPoolAgentAdvice.class)
                            .on(isConstructor())
                    );
                    return builder;
                })
                // 支持重新定义已存在的类
                .with(AgentBuilder.RedefinitionStrategy.REDEFINITION)
                .installOn(inst);
    }


}
