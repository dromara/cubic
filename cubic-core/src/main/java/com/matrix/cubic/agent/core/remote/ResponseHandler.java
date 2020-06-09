package com.matrix.cubic.agent.core.remote;

/**
 * @ClassName ResponseHandler
 * @Author QIANGLU
 * @Date 2020/4/21 2:45 下午
 * @Version 1.0
 */
public interface ResponseHandler {

    void handle(String line);

    void handleEOF();

    void handleError(Throwable t);
}
