package com.matrix.agent.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程配置
 *
 * @author QIANGLU on 2019/9/26
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {

    @Value("${sms.executor.corePoolSize:5}")
    private int corePoolSize;

    @Value("${sms.executor.maxPoolSize:10}")
    private int maxPoolSize;

    @Value("${sms.executor.queueCapacity:200}")
    private int queueCapacity;

    @Bean(name = "mailAsync")
    public Executor mailAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MailExecutor-");
        executor.initialize();
        return executor;
    }

}