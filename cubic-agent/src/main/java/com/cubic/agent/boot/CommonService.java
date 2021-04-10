package com.cubic.agent.boot;

/**
 * @ClassName BootService
 * @Author QIANGLU
 * @Date 2020/6/8 10:36 上午
 * @Version 1.0
 */
public interface CommonService {

    /**
     * 数据预处理
     */
    void prepare();

    /**
     * 启动功能
     */
    void start();

    /**
     * 停止功能
     */
    void shutdown();

    /**
     * 初始化完毕
     */
    void complete();
}
