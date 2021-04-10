package com.cubic.proxy.common.webserver;

/**
 * @ClassName WebProcess
 * @Author QIANGLU
 * @Date 2020/4/8 3:47 下午
 * @Version 1.0
 */
public interface WebProcess {

    String command();

    String process(String cmd);
}
