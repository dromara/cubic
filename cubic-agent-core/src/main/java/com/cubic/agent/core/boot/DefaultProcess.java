package com.cubic.agent.core.boot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于接收命令进行处理
 * @ClassName DefaultProcess
 * @Author QIANGLU
 * @Date 2020/6/8 11:00 上午
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefaultProcess {
}
