package com.matrix.agent.factory;

/**
 * @ClassName Task
 * @Author QIANGLU
 * @Date 2020/4/21 10:17 上午
 * @Version 1.0
 */
public interface Task {

    String getId();

    void execute();

    void cancel();
}
