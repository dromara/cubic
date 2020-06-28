package com.matrix.cubic.agent.core.conf;

/**
 * @ClassName ConfigNotFoundException
 * @Author QIANGLU
 * @Date 2020/5/19 10:15 上午
 * @Version 1.0
 */
public class CubicConfigNotFoundException extends Exception{

    public CubicConfigNotFoundException(String message, Throwable t){
        super(message,t);
    }

    public CubicConfigNotFoundException(String message){
        super(message);
    }
}
