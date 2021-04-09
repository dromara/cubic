package com.cubic.agent.factory;


import com.cubic.agent.remote.ResponseHandler;

/**
 * @ClassName TaskFactory
 * @Author QIANGLU
 * @Date 2020/4/21 10:38 上午
 * @Version 1.0
 */
public interface TaskFactory<T> {

    Integer code();

    String name();

    Task create(String id, String command, String pid, ResponseHandler handler);


}
