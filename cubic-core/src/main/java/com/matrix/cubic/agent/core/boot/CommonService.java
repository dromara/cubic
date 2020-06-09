package com.matrix.cubic.agent.core.boot;

/**
 * @ClassName BootService
 * @Author QIANGLU
 * @Date 2020/6/8 10:36 上午
 * @Version 1.0
 */
public interface CommonService {

    void prepare();

    void start();

    void shutdown();

    void complete();
}
